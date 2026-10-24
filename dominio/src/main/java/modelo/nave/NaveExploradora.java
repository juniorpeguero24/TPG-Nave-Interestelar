package modelo.nave;

import modelo.universo.Posicion;

public class NaveExploradora extends Nave {
    public NaveExploradora(String nombre, Posicion posicion) {
        super(nombre, 60, 80, posicion);
    }

    @Override
    public String getTipo() {
        return "exploradora";
    }
}
