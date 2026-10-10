package modelo.nave;

import java.util.ArrayList;

import paquete.bitacora.Bitacora;
import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;
import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;
import paquete.motorwarp.MotorWarp;
import paquete.tripulacion.Tripulacion;

public abstract class Nave {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;


    public Nave( String nombre,int combustible,int energia) {
        this.nombre = nombre;
        this.motorWarp = new MotorWarp();
        this.tripulacion = new Tripulacion();
        this.recursos = new Recursos(combustible, energia);
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getEstadoActual() {
        return " Combustible: " + recursos.getCombustible()
                + ", Energia: " + recursos.getEnergia()
                + ", Desgaste: " + recursos.getDesgaste()
                + ", Requiere mantenimiento: " + recursos.requiereMantenimiento()
                + ", Motor Warp: " + motorWarp.getEstado();
    }
    
    // Operaciones delegadas sobre Tripulacion
    public void agregarTripulante(Tripulante tripulante) {
        this.tripulacion.agregarTripulante(tripulante);
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulacion.getTripulantes();
    }
    
    public boolean tieneTripulacionValida() {
        return tripulacion.esValida();
    }

    public ArrayList<Liquidacion> liquidarHaberesTripulacion() {
        return tripulacion.liquidarHaberes();
    }
    
    // Operaciones delegadas sobre Recursos 
    public int getCombustible() {
        return recursos.getCombustible();
    }

    public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.cargarCombustible(cantidad);
    }

    public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirCombustible(cantidad);
    }
    
    public int getEnergia() {
        return recursos.getEnergia();
    }
    
    public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.cargarEnergia(cantidad);
    }
    
    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(cantidad);
    }
    
    public int getDesgaste() {
        return recursos.getDesgaste();
    }
    
    public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.aumentarDesgaste(cantidad);
    }
    
    public void realizarMantenimiento(){
        recursos.realizarMantenimiento();
    }
    
    public boolean requiereMantenimiento(){
        return recursos.requiereMantenimiento();
    }  
    
    public void verificarDisponibilidadRecursos(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        recursos.verificarDisponibilidadRecursos(combustibleNecesario,energiaNecesaria,desgasteGenerado);
    }
    
    // Operaciones delegadas sobre el Motor Warp
    public String getEstadoMotorWarp() {
        return motorWarp.getEstado();
    }

    public Evento prepararSalto(){
        return motorWarp.prepararSalto();
    }

    public Evento saltar(){
        return motorWarp.saltar(); 
    }

    public Evento enfriar(){
        return motorWarp.enfriar();
    }

    public String obtenerEstadoActualMotor() {
        return this.motorWarp.getEstado();
    }
}
