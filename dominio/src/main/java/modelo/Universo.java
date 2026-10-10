package modelo;

import java.util.ArrayList;

public class Universo {
    private final ArrayList<AsistenteComando> asistentes;
    private AsistenteComando asistenteEnOperacion;

    public Universo() {
        this.asistentes = new ArrayList<>();
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

    public AsistenteComando getAsistenteEnOperacion() {
        return asistenteEnOperacion;
    }

    public int getCantidadAsistentes() {
        return this.asistentes.size();
    }
}