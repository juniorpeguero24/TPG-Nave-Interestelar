package modelo.haberes;

import modelo.tripulacion.Cargo;

public class SueldoBase implements Liquidacion{
    private final double sueldo;
    private final Cargo cargo;
    private final String nombre;

    public SueldoBase(String nombre, Cargo cargo){
        this.nombre = nombre;
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

    @Override
    public double calcularTotal() {
        return this.sueldo;
    }

    @Override
    public String obtenerDetalle() {
        return "Sueldo base ("+this.cargo+" "+this.nombre+"): " + this.sueldo + " PG";
    }
}
