package paquete.haberes;

import paquete.Origen;

public class SubsidioOrigen extends LiquidacionDecorator{
    private final Origen origen;
    private final double montoSubsidio;

    public SubsidioOrigen(Liquidacion envoltorio, Origen origen) {
        super(envoltorio);
        this.origen=origen;
        if (origen.equals(Origen.TERRICOLA))
            this.montoSubsidio=20;
        else if (origen.equals(Origen.VULCANO))
            this.montoSubsidio=30;
        else if (origen.equals(Origen.MARCIANO))
            this.montoSubsidio=18;
        else this.montoSubsidio=0;
    }

    @Override
    public double calcularTotal() {
        return super.calcularTotal()+this.montoSubsidio;
    }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle()+ "\nSubsidio por "+this.origen+": "+this.montoSubsidio+" PG";
    }
}
