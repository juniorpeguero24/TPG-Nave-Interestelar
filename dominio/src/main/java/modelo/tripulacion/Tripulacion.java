package modelo.tripulacion;

import modelo.excepciones.CapitanExistenteException;
import modelo.excepciones.TripulanteInvalidoException;
import modelo.excepciones.TripulanteRepetidoException;
import modelo.haberes.Liquidacion;

import java.util.ArrayList;

public class Tripulacion {
    private ArrayList<Tripulante> tripulantes;

    public Tripulacion() {
        this.tripulantes = new ArrayList<>();
    }

    public void agregarTripulante(Tripulante tripulante) throws CapitanExistenteException, TripulanteRepetidoException, TripulanteInvalidoException {
        if (tripulante == null) {
            throw new TripulanteInvalidoException("El tripulante no puede ser nulo");
        }
        if (tripulantes.contains(tripulante)) {
            throw new TripulanteRepetidoException("El tripulante ya pertenece a la tripulacion");
        }
        if (tripulante.getCargo() == Cargo.CAPITAN && tieneCapitan()) {
            throw new CapitanExistenteException("Ya existe un capitan en la tripulacion");
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
        return new ArrayList<>(tripulantes); // devuelve una copia
    }

    public ArrayList<Liquidacion> liquidarHaberes() {
        ArrayList<Liquidacion> liquidaciones = new ArrayList<>();

        for(Tripulante t: tripulantes)
            liquidaciones.add(t.liquidarHaberes());

        return liquidaciones;
    }

    public int getCantidadTripulantes() {
        return tripulantes.size();
    }
}