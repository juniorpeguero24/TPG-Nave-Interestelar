package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.*;
import java.util.ArrayList;

import modelo.motorwarp.ResultadoSalto;
import modelo.motorwarp.TipoResultadoSalto;

import modelo.nave.Nave;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

import modelo.universo.Asteroide;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Ruta;
import observer.ObservadorMision;

public abstract class Mision {
    private static final int PAUSA_TURNO_MS = 1000;
    
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

    public final InformeMision realizarMision(AsistenteComando asistente) throws NaveDestruidaExcepcion {
        InformeMision informe = null;
        try {
            preparar(asistente);
            ejecutar(asistente);
            informe = evaluar(asistente);
            cerrar(asistente);
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion | TripulacionInvalidaExcepcion | InterruptedException e) {
            this.resultado = ResultadoMision.FALLO;
            observaciones.add(e.getMessage());
            informe = generarInforme(asistente);
        } catch (NaveDestruidaExcepcion e) {
            this.resultado = ResultadoMision.FALLO;
            observaciones.add(e.getMessage());
            informe = generarInforme(asistente);
            throw e;
        }
        return informe;
    }

    protected void preparar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, TripulacionInvalidaExcepcion, NaveDestruidaExcepcion {
        if (!asistente.tieneTripulacionValida()) {
            throw new TripulacionInvalidaExcepcion("No se puede realizar una mision con una tripulacion invalida");
        }
        if (asistente.naveDestruida()) {
            throw new NaveDestruidaExcepcion("No se puede iniciar una mision con la nave destruida");
        }

        // Verificamos los recursos necesarios para iniciar.
        // Incluimos los 5 de energia del radar largo,
        // porque se consume durante la preparacion.
        asistente.verificarDisponibilidadParaMision(
                CostosMision.COSTO_BASE_COMBUSTIBLE,
                getEnergiaInicial() + CostosMision.COSTO_RADAR_LARGO,
                CostosMision.COSTO_BASE_DESGASTE
        );
            
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));

        // Se verifica todo junto antes de consumir nada: si falta algo, no queda ningun cambio parcial.
        int energiaInicial = getEnergiaInicial();
        asistente.verificarDisponibilidadRecursos(
                CostosMision.COSTO_BASE_COMBUSTIBLE,
                energiaInicial + CostosMision.COSTO_RADAR_LARGO,
                CostosMision.COSTO_BASE_DESGASTE);

        asistente.consumirCombustible(CostosMision.COSTO_BASE_COMBUSTIBLE);
        if (energiaInicial > 0) {
            asistente.consumirEnergia(energiaInicial);
        }
        asistente.aumentarDesgaste(CostosMision.COSTO_BASE_DESGASTE);
        sumarCombustibleConsumido(CostosMision.COSTO_BASE_COMBUSTIBLE);
        sumarEnergiaConsumida(energiaInicial);
        sumarDesgasteGenerado(CostosMision.COSTO_BASE_DESGASTE);

        Posicion origen = tablero.getPosicionNave();
        this.objetivo = elegirObjetivo(origen);
        ruta = asistente.usarRadarLargoAlcance(origen, this.objetivo);
        if (asistente.naveDestruida()) {
            throw new NaveDestruidaExcepcion("La nave fue destruida durante la preparacion");
        }
        energiaConsumida += CostosMision.COSTO_RADAR_LARGO;
    }
    
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        int combustible = CostosMision.COSTO_BASE_COMBUSTIBLE;
        int energia = getEnergiaInicial();
        int desgaste = CostosMision.COSTO_BASE_DESGASTE;

        // Verificacion antes de modificar recursos.
        asistente.verificarDisponibilidadRecursos(combustible, energia, desgaste);
        // Consumo efectivo de despegue.
        asistente.consumirCombustible(combustible);
        if (energia > 0) {
            asistente.consumirEnergia(energia);
        }
        asistente.aumentarDesgaste(desgaste);
        
        // Actualizamos los valores del informe.
        sumarCombustibleConsumido(combustible);
        sumarEnergiaConsumida(energia);
        sumarDesgasteGenerado(desgaste);

        agregarAccionPrincipal("Consumo de recursos iniciales");
            
        ResultadoSalto ultimoResultado = null;
        while ((ruta.tieneSiguiente() || (ultimoResultado != null && ultimoResultado.debeEnfriar())) && !asistente.naveDestruida()) {

            // A. TURNO DE LA NAVE

            // 1. Inspección e interacción
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

            // 3. Planificación y movimiento
            if (ultimoResultado != null && ultimoResultado.debeEnfriar()) {
                // Turno de enfriamiento (sin desplazamiento)
                ultimoResultado = realizarMovimiento(asistente, false);
                if (ultimoResultado.getTipo() != TipoResultadoSalto.ENFRIAMIENTO) {
                    agregarObservacion("No se pudo completar el enfriamiento");
                    break;
                }
            } else {
                boolean usarWarp = ultimoResultado != null && ultimoResultado.debeContinuarWarp();
                
                // Solo decidimos un nuevo salto si no hay una preparación Warp en curso.
                if (!usarWarp) {
                    Posicion actual = tablero.getPosicionNave();
                    Posicion siguiente = ruta.verSiguiente();
                    ElementoDelUniverso siguienteElemento = obtenerElementoEnSiguientePosicion(alrededores, actual, siguiente);
                    // Uso el saltoWarp para saltar asteroides y naves
                    usarWarp = siguienteElemento instanceof Asteroide || siguienteElemento instanceof Nave;
                }
                int casillerosNecesarios = usarWarp ? 2 : 1;
                
                // Validar antes de ordenar el movimiento
                if (!ruta.puedeAvanzar(casillerosNecesarios)) {
                    agregarObservacion("No hay suficientes posiciones para realizar el salto");
                    break;
                }
                ultimoResultado = realizarMovimiento(asistente, usarWarp);
                if (ultimoResultado.getTipo() == TipoResultadoSalto.INVALIDO) {
                    agregarObservacion(ultimoResultado.getDescripcion());
                    break;
                }
                if (asistente.naveDestruida()) {
                    break;
                }

                // Mover únicamente cuando corresponde
                if (ultimoResultado.huboMovimiento()) {
                    Posicion nuevaPosicion = ruta.siguiente(ultimoResultado.getCasillerosAvanzados());
                    tablero.moverNave(nuevaPosicion);
                }
            }

            if (asistente.naveDestruida()) {
                break;
            }

            // B. TURNO DEL TABLERO
            destino.turnoDestino();
            
            // Pausa breve para la simulacion
            Thread.sleep(PAUSA_TURNO_MS);
        }
    }

    protected abstract InformeMision evaluar(AsistenteComando asistente);

    protected InformeMision generarInforme(AsistenteComando asistente) {
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
    
    // Cierre: las misiones lo redefinen para entregar o recibir recursos y llaman a super.cerrar.
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (asistente.naveDestruida()) {
            agregarObservacion("La nave fue destruida durante la misión");
        } else if (resultado == ResultadoMision.EXITO) {
            asistente.registrarEvento(new Evento(TipoEvento.MISION, "Misión cumplida: " + nombre));
        } else {
            agregarObservacion("La misión no alcanzó su objetivo");
        }
    }

    public String getNombre() {
        return nombre;
    }
    
    // Energia extra que consume la mision al comenzar (segun el anexo).
    protected abstract int getEnergiaInicial();
        
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

    // Por defecto el objetivo lo sortea el tablero;
    // M-03 lo redefine.
    protected Posicion elegirObjetivo(Posicion origen) {
        return tablero.obtenerObjetivo();
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