package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.*;
import java.util.ArrayList;

import modelo.universo.Posicion;
import modelo.universo.Ruta;

public abstract class Mision {
    private final String nombre;
    private int combustibleConsumido, energiaConsumida, desgasteGenerado;
    protected ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;
    private ArrayList<String> observaciones;
    private Posicion destino;
    private Ruta ruta;

    public Mision(String nombre, Posicion destino) {
        this.nombre = nombre;
        this.destino = destino;
        this.accionesPrincipales = new ArrayList<>();
        this.observaciones = new ArrayList<>();
        this.resultado = ResultadoMision.FALLO;
        this.combustibleConsumido = 0;
        this.energiaConsumida = 0;
        this.desgasteGenerado = 0;
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
        ruta = asistente.usarRadarLargoAlcance(destino);
        energiaConsumida += CostosMision.COSTO_RADAR_LARGO;
    }
    
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        while (ruta.tieneSiguiente() && !asistente.naveDestruida()) {
               Posicion siguiente = ruta.siguiente();

               // 1. Inspeccionar posición actual
               // 2. Radar corto
               // 3. Planificar y mover

               asistente.avanzarA(siguiente);
               agregarAccionPrincipal("La nave avanzo a " + siguiente);

               combustibleConsumido += CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE;
               desgasteGenerado += CostosMision.COSTO_MOVIMIENTO_DESGASTE;
            
               // 4. Turno del tablero
            
               if (asistente.naveDestruida()) {
                   resultado = ResultadoMision.FALLO;
                   agregarObservacion("La nave fue destruida durante la mision");
               }
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

    protected Posicion getDestino() {
        return destino;
    }
}