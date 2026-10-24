package modelo.universo;

import modelo.nave.Nave;

public interface ElementoDelUniverso {
    Posicion getPosicion();
    void setPosicion(Posicion posicion);
    void interactuarCon(Nave nave);
}