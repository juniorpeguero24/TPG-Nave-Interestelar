package modelo.nave;

import java.util.ArrayList;
import java.util.List;

import modelo.universo.Posicion;
import modelo.universo.Ruta;

public class RadarLargoAlcance {
    public RadarLargoAlcance() { // Tiene un constructor vacio pero debe ser un componente de la nave
    }
    
    public Ruta calcularRuta(Posicion origen, Posicion destino) {
        List<Posicion> posiciones = new ArrayList<>();
        int xActual = origen.getX();
        int yActual = origen.getY();

        // Primero avanzo en x
        while (xActual != destino.getX()) {
            if (xActual < destino.getX()) {
                xActual++;
            } else {
                xActual--;
            }
            posiciones.add(new Posicion(xActual, yActual));
        }

        // Despues avanzo en y
        while (yActual != destino.getY()) {
            if (yActual < destino.getY()) {
                yActual++;
            } else {
                yActual--;
            }
            posiciones.add(new Posicion(xActual, yActual));
        }

        return new Ruta(posiciones);
    }
}