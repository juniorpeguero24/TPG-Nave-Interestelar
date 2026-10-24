package modelo.universo;

public class Asteroide implements ElementoDelUniverso {
    private Posicion posicion;
    private int danioEnergia;

    public Asteroide(Posicion posicion, int danioEnergia) {
        this.posicion = posicion;
        this.danioEnergia = danioEnergia;
    }

    @Override
    public Posicion getPosicion() {
        return posicion;
    }

    @Override
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getDanioEnergia() {
        return danioEnergia;
    }
}