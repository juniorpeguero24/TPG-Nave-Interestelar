package modelo.universo;

import modelo.nave.Nave;

public interface ElementoDelUniverso {
    ResultadoInteraccion interactuarCon(Nave nave);
}