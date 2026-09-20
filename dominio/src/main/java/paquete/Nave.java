package paquete;

import java.util.ArrayList;

import paquete.bitacora.Bitacora;
import paquete.motorwarp.MotorWarp;

public abstract class Nave {
    private String nombre;
    private int combustible, energia,desgaste;
    private MotorWarp motorWarp;
    private ArrayList<Tripulante> tripulantes;
    private Bitacora bitacora;
    private AsistenteComando asistente;

    private static final int CAPACIDAD_MAX_COMBUSTIBLE = 100;
    private static final int CAPACIDAD_MAX_ENERGIA = 100;
    private static final int LIMITE_DESGASTE_MANTENIMIENTO = 80;

    public Nave( String nombre,int combustible,int energia) {
        this.nombre = nombre;
        this.combustible = combustible;
        this.energia = energia;
        this.desgaste = 0;
        this.motorWarp = new MotorWarp();
        this.tripulantes = new ArrayList<>();
        this.bitacora = new Bitacora();
        this.asistente = new AsistenteComando(this.motorWarp, this.bitacora);
    }

    public void agregarTripulante(Tripulante t){
        if (t != null && !this.tripulantes.contains(t))
            tripulantes.add(t);
    }
    
    public void cargarCombustible(int cantidad){
        if (cantidad > 0 && (this.combustible+ cantidad) <= CAPACIDAD_MAX_COMBUSTIBLE)
            this.combustible += cantidad;
    }
    
    public void cargaEnergia(int cantidad){
        if (cantidad > 0 && (this.energia + cantidad) <= CAPACIDAD_MAX_ENERGIA)
            this.energia += cantidad;
    }
    
    public void realizarMantenimiento(){
        this.desgaste = 0;
    }
    
    public boolean requiereMantenimiento(){
        return this.desgaste >= LIMITE_DESGASTE_MANTENIMIENTO;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }

    public int getCombustible() {
        return combustible;
    }

    public int getEnergia() {
        return energia;
    }

    public int getDesgaste() {
        return desgaste;
    }

    public AsistenteComando getAsistenteComando() {
        return asistente;
    }

}
