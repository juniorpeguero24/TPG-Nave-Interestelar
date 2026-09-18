package paquete;

public class NaveFactory {
    public static Nave crearNave(int id,String nombre,String tipoClase){
        double masa;
        
        switch (tipoClase.toLowerCase()){
            case "exploradora":
                masa = 1200.5;
                break;
            case "carguero":
                masa = 8500;
                break;
            case "combate":
                masa = 3400;
                break;
            default:
                masa=2000;
            tipoClase = "estandar";
            break;
        }
        return new Nave(id,nombre,tipoClase,masa);
    }
}
