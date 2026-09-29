package paquete.mision;

import java.util.ArrayList;

import paquete.Nave;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public abstract class Mision {
    protected Nave nave;
    private String nombre;
    private int combustibleNecesario, energiaNecesaria, desgasteGenerado;
    protected ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;
    private ArrayList<String> observaciones;

    public Mision(Nave nave, String nombre, int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) {
        this.nave = nave;
        this.nombre = nombre;
        this.combustibleNecesario = combustibleNecesario;
        this.energiaNecesaria = energiaNecesaria;
        this.desgasteGenerado = desgasteGenerado;
        accionesPrincipales = new ArrayList<>();
        observaciones = new ArrayList<>();
    }
    
    public InformeMision realizarMision() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion { // Template Method
        preparar();
        ejecutar();
        evaluar();
        return cerrar();
    }

    protected void preparar() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        nave.verificarDisponibilidadRecursos(combustibleNecesario, energiaNecesaria, desgasteGenerado);
    }
    
    protected abstract void ejecutar() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;
    
    protected abstract void evaluar();
    
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