package modelo.haberes;

import modelo.tripulacion.Cargo;

public class Antiguedad extends LiquidacionDecorator{
    private final int antiguedad;
    private final double adicional;

    public Antiguedad(Liquidacion envoltorio, Cargo cargo, int antiguedad) {
        super(envoltorio);
        if (envoltorio == null) {
            throw new IllegalArgumentException("El concepto a envolver no puede ser nulo.");
        }
        if (cargo == null) {
            throw new IllegalArgumentException("El cargo no puede ser nulo.");
        }
        if (antiguedad < 0) {
            throw new IllegalArgumentException("La antigüedad no puede ser negativa.");
        }
        if (cargo.equals(Cargo.CAPITAN))
            this.adicional=(1000*0.2)*antiguedad;
        else if (cargo.equals(Cargo.CONSEJERO))
            this.adicional=(600*0.05)*antiguedad;
        else if (cargo.equals(Cargo.TENIENTE))
            this.adicional=(400*0.03)*antiguedad;
        else if (cargo.equals(Cargo.ALFEREZ))
            this.adicional=(200*0.005)*antiguedad;
        else this.adicional=0;
        this.antiguedad=antiguedad;
    }

    @Override
    public double calcularTotal() {
        return super.calcularTotal()+this.adicional;
    }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle()+"\nAdicional por "+this.antiguedad+" años de antiguedad: "+this.adicional+" PG";
    }
}
