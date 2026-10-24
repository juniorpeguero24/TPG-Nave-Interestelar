package modelo.nave;

import java.util.ArrayList;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.bitacora.Evento;
import modelo.motorwarp.MotorWarp;
import modelo.tripulacion.Tripulacion;
import modelo.Recursos;
import modelo.haberes.Liquidacion;
import modelo.tripulacion.Tripulante;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Posicion;

public abstract class Nave implements ElementoDelUniverso {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;
    private Posicion posicion;


    public Nave( String nombre,int combustible,int energia, Posicion posicion) {
        this.nombre = nombre;
        this.motorWarp = new MotorWarp();
        this.tripulacion = new Tripulacion();
        this.recursos = new Recursos(combustible, energia);
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public Posicion getPosicion() {
        return posicion;
    }

    @Override
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
    
    // Usado en Prueba para validar las construcciones correctas
    public abstract String getTipo();
    
    public String getEstadoActual() {
        return "\n  Combustible: " + recursos.getCombustible()
                + "\n  Energia: " + recursos.getEnergia()
                + "\n  Desgaste: " + recursos.getDesgaste()
                + "\n  Requiere mantenimiento: " + recursos.requiereMantenimiento()
                + "\n  Motor Warp: " + motorWarp.getEstado();
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
    
    // gasto voluntario
    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(cantidad);
    }
    
    // gasto involuntario
    public void recibirDanioEnergia(int cantidad) {
        recursos.reducirEnergia(cantidad);
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
    
    // Encuentros entre naves
    public abstract ResultadoEncuentro enfrentar(Nave otraNave);

    protected abstract ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora);

    protected abstract ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero);

    protected abstract ResultadoEncuentro enfrentarConCombate(NaveCombate combate);
    
    @Override
    public void interactuarCon(Nave nave) {
        ResultadoEncuentro resultado = nave.enfrentar(this);
        // aplicamos las consecuencias si pierde
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
}
