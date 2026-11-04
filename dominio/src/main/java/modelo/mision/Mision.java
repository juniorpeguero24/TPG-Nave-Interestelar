package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.*;
import java.util.ArrayList;

import modelo.motorwarp.ResultadoSalto;
import modelo.motorwarp.TipoResultadoSalto;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

import modelo.universo.Asteroide;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Ruta;
import observer.ObservadorMision;

public abstract class Mision {
    private final String nombre;
    private int combustibleConsumido, energiaConsumida, desgasteGenerado;
    protected ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;
    private ArrayList<String> observaciones;
    private Destino destino;
    private Tablero tablero;
    private Ruta ruta;
    private final ArrayList<ObservadorMision> observadores;
    private Posicion objetivo;

    public Mision(String nombre, Destino destino, Tablero tablero) {
        this.nombre = nombre;
        this.destino = destino;
        this.tablero = tablero;
        this.accionesPrincipales = new ArrayList<>();
        this.observaciones = new ArrayList<>();
        this.resultado = ResultadoMision.FALLO;
        this.combustibleConsumido = 0;
        this.energiaConsumida = 0;
        this.desgasteGenerado = 0;
        this.observadores = new ArrayList<>();
        this.objetivo = null;
    }

    protected Tablero getTablero() {
        return tablero;
    }

    protected Destino getDestino() {
        return destino;
    }

    public final InformeMision realizarMision(AsistenteComando asistente) {
        
        try {
            preparar(asistente);
            ejecutar(asistente);
            evaluar(asistente);
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion | TripulacionInvalidaExcepcion | InterruptedException e) {
            this.resultado = ResultadoMision.FALLO;
            observaciones.add(e.getMessage());
        }

        return finalizar(asistente);
    }

    protected void preparar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, TripulacionInvalidaExcepcion {
        if (!asistente.tieneTripulacionValida()) {
            throw new TripulacionInvalidaExcepcion("No se puede realizar una mision con una tripulacion invalida");
        }
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));
        Posicion origen = tablero.getPosicionNave();
        this.objetivo = tablero.obtenerObjetivo();
        ruta = asistente.usarRadarLargoAlcance(origen, this.objetivo);
        energiaConsumida += CostosMision.COSTO_RADAR_LARGO;
    }
    
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        boolean warpEnCurso = false;
        boolean enfriamientoPendiente = false;
        
        while ((ruta.tieneSiguiente() || enfriamientoPendiente) && !asistente.naveDestruida()) {

            // Llegue al objetivo pero todavia tengo el turno de enfriamiento
            if (!ruta.tieneSiguiente() && enfriamientoPendiente) {
                ResultadoSalto resultadoSalto = realizarMovimiento(asistente, false);
                if (resultadoSalto.getTipo() == TipoResultadoSalto.ENFRIAMIENTO) {
                    enfriamientoPendiente = false;
                }
                if (!asistente.naveDestruida()) {
                    destino.turnoDestino();
                }
                continue;
            }

            // 1. Inspección de posición actual
            ElementoDelUniverso elemento = tablero.inspeccionarPosicion();
            if (elemento != null) {
                asistente.interactuarCon(elemento);
            }
            if (asistente.naveDestruida()) {
                break;
            }

            // 2. Radar corto
            ElementoDelUniverso[][] alrededores = asistente.usarRadarCortoAlcance(tablero);
            sumarEnergiaConsumida(CostosMision.COSTO_RADAR_CORTO);
            if (asistente.naveDestruida()) {
                break;
            }
            Posicion actual = tablero.getPosicionNave();
            Posicion verSiguiente = ruta.verSiguiente();
            ElementoDelUniverso siguienteElemento = obtenerElementoEnSiguientePosicion(alrededores, actual, verSiguiente);
            
            // Si ya habia un warp en curso o el obstaculo es un asteroide, salto con warp
            boolean debeUsarWarp = warpEnCurso || siguienteElemento instanceof Asteroide;
            
            // 3. Ejecuto el salto correspondiente
            ResultadoSalto resultadoSalto = realizarMovimiento(asistente, debeUsarWarp);
            
            // 5. Interpretar el resultado del State
                switch (resultadoSalto.getTipo()) {
                    case PREPARANDO:
                    case WARP_LISTO:
                    case SIN_ENERGIA:
                        warpEnCurso = true;
                        break;
                    case SALTO_WARP:
                        warpEnCurso = false;
                        enfriamientoPendiente = true;
                        break;
                    case ENFRIAMIENTO:
                        enfriamientoPendiente = false;
                        break;
                    case INVALIDO:
                        agregarObservacion(resultadoSalto.getDescripcion());
                        break;
                    case SALTO_NORMAL:
                        break;
                }
            
            // 6. Si realmente hubo movimiento muevo la nave
            int casilleros = resultadoSalto.getCasillerosAvanzados();
            if (casilleros > 0) {
                Posicion nuevaPosicion = ruta.siguiente(casilleros);
                if (nuevaPosicion == null) {
                    agregarObservacion("No fue posible avanzar " + casilleros + " posiciones en la ruta");
                    break;
                }
                tablero.moverNave(nuevaPosicion);
            }
            
            // consultar si despues de moverme tengo que interactuar de nuevo
            
            if (asistente.naveDestruida()) {
                break;
            }

            // 7. Turno del universo
            destino.turnoDestino();
            
            // 8. Pausa breve para la simulacion (1.5s por ciclo)
            Thread.sleep(1500);
        }
    }

    protected abstract void evaluar(AsistenteComando asistente);

    protected InformeMision finalizar(AsistenteComando asistente) {
        InformeMision informe = new InformeMision(
            nombre,
            resultado,
            accionesPrincipales,
            combustibleConsumido,
            energiaConsumida,
            desgasteGenerado,
            asistente.obtenerEstadoNaveActual(),
            observaciones
        );

        asistente.registrarEvento(new Evento(TipoEvento.INFORME, informe.toString()));
        notificarObservadores(informe);

        return informe;
    }

    public String getNombre() {
        return nombre;
    }

    protected void sumarCombustibleConsumido(int cantidad) {
        combustibleConsumido += cantidad;
    }

    protected void sumarEnergiaConsumida(int cantidad) {
        energiaConsumida += cantidad;
    }

    protected void sumarDesgasteGenerado(int cantidad) {
        desgasteGenerado += cantidad;
    }

    protected void agregarObservacion(String observacion) {
        observaciones.add(observacion);
    }

    protected void agregarAccionPrincipal(String accion) {
        accionesPrincipales.add(accion);
    }
    public String getInforme(int combustibleConsumido, int energiaConsumida, int desgasteGenerado, String estadoFinalNave) {
        return "La mision " + getNombre() + " fue un " + resultado.toString() + ". Se consumio " + combustibleConsumido + " de combustible y " +
                energiaConsumida + " de energia. El desgaste generado fue de " + desgasteGenerado + " y la nave quedo en estado " + estadoFinalNave;
    }
    
    protected Ruta getRuta() {
        return ruta;
    }
    
    private ElementoDelUniverso obtenerElementoEnSiguientePosicion(ElementoDelUniverso[][] alrededores, Posicion actual, Posicion siguiente) {
        int dx = siguiente.getX() - actual.getX();
        int dy = siguiente.getY() - actual.getY();
        return alrededores[dx + 1][dy + 1]; // La nave esta en [1][1]
    }

    protected boolean llegoAlObjetivo(AsistenteComando asistente) {
        return !asistente.naveDestruida() && objetivo != null && tablero.getPosicionNave().equals(objetivo);
    }

    // OBSERVER

    public void añadirObservador(ObservadorMision observador) {
        this.observadores.add(observador);
    }

    public void quitarObservador(ObservadorMision observador) {
        this.observadores.remove(observador);
    }

    public void notificarObservadores(InformeMision informe) {
        for (ObservadorMision observador : observadores) {
            observador.actualizarInformes(informe);
        }
    }
    
    private ResultadoSalto realizarMovimiento(AsistenteComando asistente, boolean usarWarp) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        int combustibleAntes = asistente.getCombustible();
        int energiaAntes = asistente.getEnergia();
        int desgasteAntes = asistente.getDesgaste();

        ResultadoSalto resultadoSalto;
        if (usarWarp) {
            resultadoSalto = asistente.saltarWarp();
        } else {
            resultadoSalto = asistente.saltar();
        }

        int combustibleDespues = asistente.getCombustible();
        int energiaDespues = asistente.getEnergia();
        int desgasteDespues = asistente.getDesgaste();

        sumarCombustibleConsumido(combustibleAntes - combustibleDespues);
        sumarEnergiaConsumida(energiaAntes - energiaDespues);
        sumarDesgasteGenerado(desgasteDespues - desgasteAntes);
        
        return resultadoSalto;
    }
}