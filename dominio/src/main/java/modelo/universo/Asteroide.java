package modelo.universo;

import modelo.nave.Nave;

public class Asteroide implements ElementoDelUniverso {
    private int danioEnergia;

    public Asteroide(int danioEnergia) {
        this.danioEnergia = danioEnergia;
    }
    
    public int getDanioEnergia() {
        return danioEnergia;
    }
    
    @Override
    public void interactuarCon(Nave nave) {
        nave.recibirDanioEnergia(danioEnergia);
    }
    
    @Override
    public String toString() {
        return "Asteroide [danio de energia = " + danioEnergia + "]";
    }
}