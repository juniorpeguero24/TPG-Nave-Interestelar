package paquete;

public class Prueba {
    public static void main(String[] args) {
        Nave nave1 = NaveFactory.crearNave("USS Enterprise","exploradora");
        Nave nave2= NaveFactory.crearNave("Nostromo","carguero");
        
        System.out.println(nave1.toString());
        System.out.println(nave2.toString());
    }
}
