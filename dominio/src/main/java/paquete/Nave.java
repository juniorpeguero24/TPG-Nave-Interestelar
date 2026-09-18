package paquete;

import java.util.ArrayList;

public class Nave {
    private int id;
    private String nombre,clase;
    private double masa;
    private ArrayList<Tripulante> tripulantes;

    public Nave(int id, String nombre, String clase, double masa) {
        this.id = id;
        this.nombre = nombre;
        this.clase = clase;
        this.masa = masa;
        this.tripulantes = new ArrayList<>();
    }

    public void agregarTripulante(Tripulante t){
        if (t != null)
            tripulantes.add(t);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClase() {
        return clase;
    }

    public double getMasa() {
        return masa;
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }
    
    @Override
    public String toString(){
        return "Nave "+clase+" "+id+" Masa: "+masa+" Tripulantes: "+tripulantes;
    }
}
