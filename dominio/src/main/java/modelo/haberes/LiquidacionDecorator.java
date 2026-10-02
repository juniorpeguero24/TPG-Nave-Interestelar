package modelo.haberes;

public abstract class LiquidacionDecorator implements Liquidacion{
    protected final Liquidacion envoltorio;

    public LiquidacionDecorator(Liquidacion envoltorio) {
        this.envoltorio = envoltorio;
    }

    @Override
    public double calcularTotal() {
        return this.envoltorio.calcularTotal();
    }

    @Override
    public String obtenerDetalle() {
        return this.envoltorio.obtenerDetalle();
    }
}
