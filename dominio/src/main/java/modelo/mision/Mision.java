package modelo.mision;

import modelo.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.*;
import java.util.ArrayList;

public abstract class Mision {
    private final String nombre;
    private final int combustibleNecesario, energiaNecesaria, desgasteNecesario;
    private int combustibleConsumido, energiaConsumida, desgasteGenerado;
    protected ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;
    private ArrayList<String> observaciones;

    public Mision(String nombre, int combustibleNecesario, int energiaNecesaria, int desgasteNecesario) {
        this.nombre = nombre;
        this.combustibleNecesario = combustibleNecesario;
        this.energiaNecesaria = energiaNecesaria;
        this.desgasteNecesario = desgasteNecesario;
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
        asistente.verificarDisponibilidadParaMision(combustibleNecesario,energiaNecesaria,desgasteNecesario);
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));
    }
    
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        int combustibleNecesario = getCombustibleNecesario();
        asistente.consumirCombustible(combustibleNecesario);
        setCombustibleConsumido(combustibleNecesario);

        int energiaNecesaria = getEnergiaNecesaria();
        asistente.consumirEnergia(energiaNecesaria);
        setEnergiaConsumida(energiaNecesaria);

        int desgasteGenerado = getDesgasteNecesario();
        asistente.aumentarDesgaste(desgasteGenerado);
        setDesgasteGenerado(desgasteGenerado);
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

    public int getCombustibleNecesario() {
        return combustibleNecesario;
    }

    public int getEnergiaNecesaria() {
        return energiaNecesaria;
    }

    public int getDesgasteNecesario() {
        return desgasteNecesario;
    }

    protected void setCombustibleConsumido(int cantidad) {
        this.combustibleConsumido = cantidad;
    }
    
    protected void setEnergiaConsumida(int cantidad) {
        this.energiaConsumida = cantidad;
    }

    protected void setDesgasteGenerado(int cantidad) {
        this.desgasteGenerado = cantidad;
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
}