package modelo.nave;

import modelo.excepciones.NaveInvalidaException;
import modelo.excepciones.TripulanteInvalidoException;

public class NaveFactory {

    public static Nave crearNave(String nombre,String clase) throws NaveInvalidaException {
        if (nombre == null || nombre.isBlank()) {
            throw new NaveInvalidaException("El nombre no puede ser nulo");
        }
        if (clase == null || clase.isBlank()) {
            throw new NaveInvalidaException("La clase no puede ser nula");
        }
        switch (clase.toLowerCase()){
            case "exploradora":
                return new NaveExploradora(nombre);
            case "carguero":
                return new NaveCarguero(nombre);
            case "combate":
                return new NaveCombate(nombre);
            default: throw new NaveInvalidaException("Tipo de nave desconocido: " + clase);
        }
    }
}
