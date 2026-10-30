package modelo.universo;

import modelo.nave.Nave;

public interface ElementoDelUniverso {
    Posicion getPosicion();
    void interactuarCon(Nave nave);
}