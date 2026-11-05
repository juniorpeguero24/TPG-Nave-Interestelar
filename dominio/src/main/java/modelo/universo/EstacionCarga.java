package modelo.universo;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.nave.Nave;

public class EstacionCarga implements ElementoDelUniverso {
    private int cargaCombustible;
    private int cargaEnergia;

    public EstacionCarga(int cargaCombustible, int cargaEnergia) {
        this.cargaCombustible = cargaCombustible;
        this.cargaEnergia = cargaEnergia;
    }

    public int getCargaCombustible() {
        return cargaCombustible;
    }

    public int getCargaEnergia() {
        return cargaEnergia;
    }
    
    @Override
    public void interactuarCon(Nave nave) {
        int combustibleATransferir = Math.min(cargaCombustible, nave.getEspacioCombustible());
        int energiaATransferir = Math.min(cargaEnergia, nave.getEspacioEnergia());
        try {
            if (combustibleATransferir > 0) {
                nave.cargarCombustible(combustibleATransferir);
            }
            if (energiaATransferir > 0) {
                nave.cargarEnergia(energiaATransferir);
            }
        } catch (OperacionRecursoInvalidaExcepcion e) {
            throw new IllegalStateException("La estación calculó una carga inválida", e);
        }
    }
    
    @Override
    public String toString() {
        return "Estacion de carga [combustible = " + cargaCombustible + ", energia = " + cargaEnergia + "]";
    }
}