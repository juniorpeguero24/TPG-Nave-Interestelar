package modelo.universo;

public class EstacionCarga implements ElementoDelUniverso {
    private Posicion posicion;
    private int combustible;
    private int energia;

    public EstacionCarga(Posicion posicion, int combustible, int energia) {
        this.posicion = posicion;
        this.combustible = combustible;
        this.energia = energia;
    }

    @Override
    public Posicion getPosicion() {
        return posicion;
    }

    @Override
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getCombustible() {
        return combustible;
    }

    public int getEnergia() {
        return energia;
    }
}