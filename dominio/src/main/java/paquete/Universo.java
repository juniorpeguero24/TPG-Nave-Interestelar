package paquete;

import java.util.ArrayList;

public class Universo {
    private final ArrayList<AsistenteComando> asistentes;
    private AsistenteComando naveEnOperacion;

    public Universo() {
        this.asistentes = new ArrayList<>();
    }

    public void registrarAsistente(AsistenteComando asistente) {
        if (asistente == null) {
            throw new IllegalArgumentException("El asistente no puede ser nulo.");
        }
        this.asistentes.add(asistente);
    }

    public AsistenteComando seleccionarNaveParaOperar(int indice) {
        if (indice < 0 || indice >= asistentes.size()) {
            throw new IndexOutOfBoundsException("Índice de nave fuera de rango.");
        }
        this.naveEnOperacion = asistentes.get(indice);
        return this.naveEnOperacion;
    }

    public AsistenteComando getNaveEnOperacion() {
        return this.naveEnOperacion;
    }

    public int getCantidadNaves() {
        return this.asistentes.size();
    }
}