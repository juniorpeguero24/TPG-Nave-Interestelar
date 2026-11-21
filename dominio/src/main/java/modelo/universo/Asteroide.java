package modelo.universo;

import modelo.excepciones.ElementoDelUniversoInvalidoExcepcion;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;

import modelo.nave.Nave;

public class Asteroide implements ElementoDelUniverso {
    private int danioEnergia;

    public Asteroide(int danioEnergia) throws ElementoDelUniversoInvalidoExcepcion {
        if (danioEnergia < 0) {
            throw new ElementoDelUniversoInvalidoExcepcion("El danio del asteroide no puede ser negativo");
        } 
        this.danioEnergia = danioEnergia;
    }
    
    public int getDanioEnergia() {
        return danioEnergia;
    }
    
    @Override
    public ResultadoInteraccion interactuarCon(Nave nave) {
        int energiaAntes = nave.getEnergia();
        try {
            nave.recibirDanioEnergia(danioEnergia);
        } catch (OperacionRecursoInvalidaExcepcion e) {
            return new ResultadoInteraccion("Error al aplicar el daño del asteroide: " + e.getMessage());
        }
        int energiaPerdida = energiaAntes - nave.getEnergia();
        return new ResultadoInteraccion("Impacto con asteroide. Energia perdida: "+ energiaPerdida);
    }
    
    @Override
    public String toString() {
        return "Asteroide [danio de energia = " + danioEnergia + "]";
    }
}
