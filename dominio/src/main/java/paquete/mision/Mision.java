package paquete.mision;

import java.util.ArrayList;

import paquete.AsistenteComando;
import paquete.Nave;

import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;
import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public abstract class Mision {
    //protected Nave nave;
    private String nombre;
    private final int combustibleNecesario, energiaNecesaria, desgasteGenerado;
    protected ResultadoMision resultado;
    //private ArrayList<String> accionesPrincipales;
    //private ArrayList<String> observaciones;

    public Mision(Nave nave, String nombre, int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) {
        //this.nave = nave;
        this.nombre = nombre;
        this.combustibleNecesario = combustibleNecesario;
        this.energiaNecesaria = energiaNecesaria;
        this.desgasteGenerado = desgasteGenerado;
        //accionesPrincipales = new ArrayList<>();
        //observaciones = new ArrayList<>();
    }

    public final void realizarMision(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        preparar(asistente);
        ejecutar(asistente);
        evaluar(asistente);
        finalizar(asistente);
    }
/*
    public InformeMision realizarMision() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion { // Template Method
        preparar();
        ejecutar();
        evaluar();
        return cerrar();
    }
*/
    protected void preparar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        asistente.verificarDisponibilidadParaMision(combustibleNecesario,energiaNecesaria);
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));
        //nave.verificarDisponibilidadRecursos(combustibleNecesario, energiaNecesaria, desgasteGenerado);
    }
    
    protected abstract void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;

    protected abstract void evaluar(AsistenteComando asistente);

    protected void finalizar(AsistenteComando asistente) {
        if (this.resultado == ResultadoMision.EXITO) {
            asistente.ordenarSaltoWarp();
            asistente.registrarEvento(new Evento(TipoEvento.MISION, "Mision " + nombre + " completada con ÉXITO."));
        } else {
            asistente.registrarEvento(new Evento(TipoEvento.MISION, "Mision " + nombre + " finalizada con FALLO."));
        }
    }
    /*
    private InformeMision cerrar() {
        return new InformeMision(
            nombre,
            resultado,
            accionesPrincipales,
            combustibleNecesario,
            energiaNecesaria,
            desgasteGenerado,
            nave.obtenerEstadoActual(),
            observaciones
        );
    }
    */
    public String getNombre() {
        return nombre;
    }
    
    protected Nave getNave() {
        return nave;
    }
    public int getCombustibleNecesario() {
        return combustibleNecesario;
    }
    public int getEnergiaNecesaria() {
        return energiaNecesaria;
    }
    public int getDesgasteGenerado() {
        return desgasteGenerado;
    }
}