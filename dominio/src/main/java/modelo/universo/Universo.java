package modelo.universo;

import java.util.ArrayList;

import modelo.excepciones.TripulanteRepetidoException;
import modelo.nave.AsistenteComando;
import modelo.tripulacion.Tripulante;

public class Universo {
    private final ArrayList<AsistenteComando> asistentes;
    private final ArrayList<Tripulante> tripulantesDisponibles;
    private AsistenteComando asistenteEnOperacion;

    public Universo() {
        this.asistentes = new ArrayList<>();
        this.tripulantesDisponibles = new ArrayList<>();
    }

    public void registrarAsistente(AsistenteComando asistente) {
        if (asistente == null) {
            throw new IllegalArgumentException("El asistente no puede ser nulo.");
        }
        this.asistentes.add(asistente);
    }

    public AsistenteComando seleccionarNave(String nombreNave) {
        for (AsistenteComando asistente : asistentes) {
            if (asistente.getNombreNave().equals(nombreNave)) {
                asistenteEnOperacion = asistente;
                return asistente;
            }
        }
        return null;
    }

    public void agregarTripulanteDisponible(Tripulante tripulante) throws TripulanteRepetidoException {
            if (tripulantesDisponibles.contains(tripulante))
                throw new TripulanteRepetidoException(("El tripulante ya esta en la lista"));

            this.tripulantesDisponibles.add(tripulante);
    }

    public void quitarTripulanteDisponible(Tripulante tripulante) {
        this.tripulantesDisponibles.remove(tripulante);
    }

    public AsistenteComando getAsistenteEnOperacion() {
        return asistenteEnOperacion;
    }

    public int getCantidadAsistentes() {
        return this.asistentes.size();
    }
}