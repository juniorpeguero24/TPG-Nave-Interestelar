package modelo.motorwarp;

public class ResultadoSalto {
    private final String descripcion;
    private final int casillerosAvanzados;
    private final TipoResultadoSalto tipo;

    ResultadoSalto(TipoResultadoSalto tipo, int casillerosAvanzados, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.casillerosAvanzados = casillerosAvanzados;
    }

    public TipoResultadoSalto getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCasillerosAvanzados() {
        return casillerosAvanzados;
    }

    public boolean huboMovimiento() {
        return casillerosAvanzados > 0;
    }
    
}