package modelo.universo;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;

import modelo.nave.Nave;

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
    
    @Override
    public void interactuarCon(Nave nave) {
        // consultar si usar esos metodos o si crear otros
        nave.cargarCombustible(combustible);
        nave.cargarEnergia(energia);
    }
}