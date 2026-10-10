package paquete.mision;

import paquete.AsistenteComando;
import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;
import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

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
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            this.resultado = ResultadoMision.FALLO;
            observaciones.add(e.getMessage());
        }

        return finalizar(asistente);
    }

    protected void preparar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        asistente.verificarDisponibilidadParaMision(combustibleNecesario,energiaNecesaria,desgasteGenerado);
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));
    }
    
    protected abstract void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;

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
}