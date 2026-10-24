package modelo.nave;

import modelo.universo.Posicion;

public class NaveFactory {
    public static Nave crearNave(String nombre,String clase, Posicion posicion){
        if (clase == null)
            return null;
        switch (clase.toLowerCase()){
            case "exploradora":
                return new NaveExploradora(nombre, posicion);
            case "carguero":
                return new NaveCarguero(nombre, posicion);
            case "combate":
                return new NaveCombate(nombre, posicion);
            default:
                return null;
        }
    }
}
