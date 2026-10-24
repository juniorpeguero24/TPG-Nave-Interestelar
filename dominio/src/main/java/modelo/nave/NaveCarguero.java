package modelo.nave;

import modelo.universo.Posicion;

public class NaveCarguero extends Nave {
    public NaveCarguero(String nombre, Posicion posicion) {
        super(nombre, 100, 60, posicion);
    }

    @Override
    public String getTipo() {
        return "carguero";
    }
}
