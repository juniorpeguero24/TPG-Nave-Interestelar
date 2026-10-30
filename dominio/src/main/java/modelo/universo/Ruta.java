package modelo.universo;

import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private List<Posicion> posiciones;
    private int indiceActual;

    public Ruta(List<Posicion> posiciones) {
        this.posiciones = new ArrayList<>(posiciones);
        this.indiceActual = 0;
    }

    public boolean tieneSiguiente() {
        return indiceActual < posiciones.size();
    }

    public Posicion siguiente() {
        if (!tieneSiguiente()) {
            return null;
        }
        Posicion siguiente = posiciones.get(indiceActual);
        indiceActual++;
        return siguiente;
    }

    public int getCantidadPosiciones() {
        return posiciones.size();
    }

    public void reiniciar() {
        indiceActual = 0;
    }
}