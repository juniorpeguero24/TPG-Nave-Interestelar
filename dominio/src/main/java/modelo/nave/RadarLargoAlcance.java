package modelo.nave;

import java.util.ArrayList;
import java.util.List;

import modelo.tablero.Posicion;

import modelo.universo.Ruta;

public class RadarLargoAlcance {
    public RadarLargoAlcance() { // Tiene un constructor vacio pero debe ser un componente de la nave
    }
    
    public Ruta calcularRuta(Posicion origen, Posicion destino) {
        List<Posicion> posiciones = new ArrayList<>();
        int xActual = origen.getX();
        int yActual = origen.getY();

        while (xActual != destino.getX() || yActual != destino.getY()) {
            // Modifico x
            if (xActual < destino.getX()) {
                xActual++;
            } else if (xActual > destino.getX()) {
                xActual--;
            }

            // Modifico y
            if (yActual < destino.getY()) {
                yActual++;
            } else if (yActual > destino.getY()) {
                yActual--;
            }

            posiciones.add(new Posicion(xActual, yActual));
        }

        return new Ruta(posiciones);
    }
}