package modelo.nave;

import modelo.tablero.Tablero;
import modelo.universo.ElementoDelUniverso;

public class RadarCortoAlcance {
    public ElementoDelUniverso[][] escanear(Tablero tablero) {
        return tablero.observarAlrededores();
    }
}