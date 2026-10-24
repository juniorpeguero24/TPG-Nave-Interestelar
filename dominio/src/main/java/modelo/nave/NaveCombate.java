package modelo.nave;

import modelo.universo.Posicion;

public class NaveCombate extends Nave {
    public NaveCombate(String nombre, Posicion posicion) {
        super(nombre, 80, 100, posicion);
    }

    @Override
    public String getTipo() {
        return "combate";
    }
}
