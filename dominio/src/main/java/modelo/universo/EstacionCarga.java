package modelo.universo;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.nave.Nave;

public class EstacionCarga implements ElementoDelUniverso {
    private int combustible;
    private int energia;

    public EstacionCarga(int combustible, int energia) {
        this.combustible = combustible;
        this.energia = energia;
    }

    public int getCombustible() {
        return combustible;
    }

    public int getEnergia() {
        return energia;
    }
    
    @Override
    public void interactuarCon(Nave nave) {
        int combustibleATransferir = Math.min(combustible, nave.getEspacioCombustible());
        int energiaATransferir = Math.min(energia, nave.getEspacioEnergia());
        try {
            nave.cargarCombustible(combustibleATransferir);
            nave.cargarEnergia(energiaATransferir);
        } catch (OperacionRecursoInvalidaExcepcion e) {
            throw new IllegalStateException("La estación calculó una carga inválida", e);
        }
    }
}