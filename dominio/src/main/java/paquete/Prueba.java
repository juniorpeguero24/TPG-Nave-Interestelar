package paquete;

public class Prueba {
    public static void main(String[] args) {
        Nave nave1 = NaveFactory.crearNave(1,"USS Enterprise","exploradora");
        Nave nave2= NaveFactory.crearNave(2,"Nostromo","carguero");
        
        System.out.println(nave1.toString());
        System.out.println(nave2.toString());
    }
}
