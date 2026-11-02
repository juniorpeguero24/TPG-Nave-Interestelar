package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.*;
import java.util.ArrayList;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

import modelo.universo.ElementoDelUniverso;
import modelo.universo.Ruta;

public abstract class Mision {
    private final String nombre;
    private int combustibleConsumido, energiaConsumida, desgasteGenerado;
    protected ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;
    private ArrayList<String> observaciones;
    private Destino destino;
    private Tablero tablero;
    private Ruta ruta;

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
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion | TripulacionInvalidaExcepcion e) {
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
        Posicion objetivo = tablero.obtenerObjetivo();
        ruta = asistente.usarRadarLargoAlcance(origen, objetivo);
        energiaConsumida += CostosMision.COSTO_RADAR_LARGO;
    }
    
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        while (ruta.tieneSiguiente() && !asistente.naveDestruida()) {

            // 1. Inspección de posición actual
            ElementoDelUniverso elemento = tablero.inspeccionarPosicion();
            if (elemento != null) {
                elemento.interactuarCon(asistente.getNave());
            }
            if (asistente.naveDestruida()) {
                break;
            }

            // 2. Radar corto
            asistente.usarRadarCortoAlcance(tablero);
            sumarEnergiaConsumida(CostosMision.COSTO_RADAR_CORTO);

            // 3. Movimiento
            Posicion siguiente = ruta.siguiente();
            asistente.avanzar();
            tablero.moverNave(siguiente);
            sumarCombustibleConsumido(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE);
            sumarDesgasteGenerado(CostosMision.COSTO_MOVIMIENTO_DESGASTE);

            // 4. Turno del universo
            destino.turnoDestino();
        }
    }

    protected abstract void evaluar(AsistenteComando asistente);

    protected InformeMision finalizar(AsistenteComando asistente) {
        
        if (resultado == ResultadoMision.EXITO) {
            asistente.ordenarSaltoWarp();
        }
        
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
    public String getInforme(
            int combustibleConsumido,
            int energiaConsumida,
            int desgasteGenerado,
            String estadoFinalNave) {
        return "La mision " + getNombre() + " fue un " + resultado.toString() + ". Se consumio " + combustibleConsumido + " de combustible y " +
                energiaConsumida + " de energia. El desgaste generado fue de " + desgasteGenerado + " y la nave quedo en estado " + estadoFinalNave;
    }
    
    protected Ruta getRuta() {
        return ruta;
    }
}