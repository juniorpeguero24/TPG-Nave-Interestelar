package paquete;

import java.util.ArrayList;

public class Tripulacion {
    private ArrayList<Tripulante> tripulantes;

    public Tripulacion() {
        this.tripulantes = new ArrayList<>();
    }

    public void agregarTripulante(Tripulante tripulante) {
        if (tripulante == null) {
            throw new IllegalArgumentException("El tripulante no puede ser nulo");
        }
        if (tripulantes.contains(tripulante)) {
            throw new IllegalArgumentException("El tripulante ya pertenece a la tripulacion");
        }
        if (tripulante.getCargo() == Cargo.CAPITAN && tieneCapitan()) {
            throw new IllegalArgumentException("Ya existe un capitan en la tripulacion");
        }

        tripulantes.add(tripulante);
    }

    public boolean tieneCapitan() {
        for (Tripulante tripulante : tripulantes) {
            if (tripulante.getCargo() == Cargo.CAPITAN) {
                return true;
            }
        }

        return false;
    }

    public boolean esValida() {
        return tripulantes.size() >= 5 && tieneCapitan();
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }

    public int getCantidadTripulantes() {
        return tripulantes.size();
    }
}