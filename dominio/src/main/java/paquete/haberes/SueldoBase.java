package paquete.haberes;

import paquete.Cargo;

public class SueldoBase implements Liquidacion{
    private final double sueldo;
    private final Cargo cargo;

    public SueldoBase(Cargo cargo){
        this.cargo=cargo;
        if (cargo.equals(Cargo.CAPITAN))
            this.sueldo=1000;
        else if (cargo.equals(Cargo.CONSEJERO))
            this.sueldo=600;
        else if (cargo.equals(Cargo.TENIENTE))
            this.sueldo=400;
        else if (cargo.equals(Cargo.ALFEREZ))
            this.sueldo=200;
        else
            this.sueldo=0;
    }

    public double getSueldo() {
        return sueldo;
    }

    @Override
    public double calcularTotal() {
        return this.sueldo;
    }

    @Override
    public String obtenerDetalle() {
        return "Sueldo base ("+this.cargo+"): " + this.sueldo + " PG";
    }
}
