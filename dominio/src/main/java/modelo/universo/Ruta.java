package modelo.universo;

import java.util.ArrayList;
import java.util.List;
import modelo.tablero.Posicion;

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

    public boolean puedeAvanzar(int cantidad) {
        return cantidad > 0 && indiceActual + cantidad <= posiciones.size();
    }

    public Posicion siguiente(int cantidad) {
        if (!puedeAvanzar(cantidad)) {
            return null;
        }
        indiceActual += cantidad;
        return posiciones.get(indiceActual - 1);
    }
    
    public Posicion verSiguiente() {
        if (!tieneSiguiente()) {
            return null;
        }
        return posiciones.get(indiceActual);
    }

    public int getCantidadPosiciones() {
        return posiciones.size();
    }

    public void reiniciar() {
        indiceActual = 0;
    }
}