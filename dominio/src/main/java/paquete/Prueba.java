package paquete;

import paquete.haberes.Liquidacion;

public class Prueba {
    public static void main(String[] args) {
        Nave nave1 = NaveFactory.crearNave("USS Enterprise","exploradora");
        Nave nave2= NaveFactory.crearNave("Nostromo","carguero");
        
        System.out.println(nave1.toString());
        System.out.println(nave2.toString());

        //Prueba liquidacion
        Tripulante trip = new Tripulante(1,10,"Carlos",Cargo.CAPITAN,Origen.TERRICOLA);

        Liquidacion liq = trip.liquidarHaberes();
        System.out.println(liq.obtenerDetalle());
        System.out.println("TOTAL: " + liq.calcularTotal() + " PG");
    }
}
