package paquete;

public class NaveFactory {
    public static Nave crearNave(String nombre,String clase){
        if (clase == null)
            return null;
        switch (clase.toLowerCase()){
            case "exploradora":
                return new NaveExploradora(nombre);
            case "carguero":
                return new NaveCarguero(nombre);
            case "combate":
                return new NaveCombate(nombre);
            default:
                return null;
        }
    }
}
