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
    public ResultadoInteraccion interactuarCon(Nave nave) {
        int energiaAntes = nave.getEnergia();
        nave.recibirDanioEnergia(danioEnergia);
        int energiaPerdida = energiaAntes - nave.getEnergia();
        return new ResultadoInteraccion("Impacto con asteroide. Energia perdida: "+ energiaPerdida);
    }
    
    @Override
    public String toString() {
        return "Asteroide [danio de energia = " + danioEnergia + "]";
    }
}