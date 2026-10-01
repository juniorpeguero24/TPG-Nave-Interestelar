package paquete.haberes;

public class AdicionalConsejeros extends LiquidacionDecorator{
    private final double montoExtra;

    public AdicionalConsejeros(Liquidacion envoltorio,int cantidadConsejos) {
        super(envoltorio);
        if (envoltorio == null) {
            throw new IllegalArgumentException("El concepto a envolver no puede ser nulo.");
        }
        if (cantidadConsejos < 0) {
            throw new IllegalArgumentException("La cantidad de consejos no puede ser negativa.");
        }
        this.montoExtra= 2*cantidadConsejos;
    }

    @Override
    public double calcularTotal() {
        return super.calcularTotal()+this.montoExtra;
    }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle()+"\nExtra por cantidad de consejos: "+this.montoExtra;
    }
}
