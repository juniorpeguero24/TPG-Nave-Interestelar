package paquete;

import java.util.ArrayList;

import paquete.bitacora.Bitacora;
import paquete.bitacora.Evento;

import paquete.motorwarp.MotorWarp;

public abstract class Nave {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;
    private Bitacora bitacora;


    public Nave( String nombre,int combustible,int energia) {
        this.nombre = nombre;
        this.motorWarp = new MotorWarp();
        this.tripulacion = new Tripulacion();
        this.bitacora = new Bitacora();
        this.recursos = new Recursos(combustible, energia);
    }

    public String getNombre() {
        return nombre;
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
    
    // Operaciones delegadas sobre Recursos 
    public int getCombustible() {
        return recursos.getCombustible();
    }

    public void cargarCombustible(int cantidad) {
        recursos.cargarCombustible(cantidad);
    }

    public void consumirCombustible(int cantidad) {
        recursos.consumirCombustible(cantidad);
    }
    
    public int getEnergia() {
        return recursos.getEnergia();
    }
    
    public void cargarEnergia(int cantidad) {
        recursos.cargarEnergia(cantidad);
    }
    
    public void consumirEnergia(int cantidad) {
        recursos.consumirEnergia(cantidad);
    }
    
    public int getDesgaste() {
        return recursos.getDesgaste();
    }
    
    public void aumentarDesgaste(int cantidad) {
        recursos.aumentarDesgaste(cantidad);
    }
    
    public void realizarMantenimiento(){
        recursos.realizarMantenimiento();
    }
    
    public boolean requiereMantenimiento(){
        return recursos.requiereMantenimiento();
    }  
    
    // Operaciones delegadas sobre Bitacora
    public void registrarEvento(Evento evento) {
        bitacora.registrarEvento(evento);
    }
    
    public String generarInformeBitacora() {
        return bitacora.toString();
    }
    
    // Operaciones delegadas sobre el Motor Warp
    public Evento prepararSalto(){
        return motorWarp.prepararSalto();
    }

    public Evento iniciarWarp(){
        return motorWarp.iniciarWarp(); 
    }

    public Evento finalizarWarp(){
        return motorWarp.finalizarWarp();
    }

    public Evento finalizarEnfriamiento(){
        return motorWarp.finalizarEnfriamiento();
    }
    
}
