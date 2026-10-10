package modelo.tripulacion;

import modelo.haberes.AdicionalConsejeros;
import modelo.haberes.Antiguedad;
import modelo.haberes.Liquidacion;
import modelo.haberes.SubsidioOrigen;
import modelo.haberes.SueldoBase;

public class Tripulante {
    private final int id;
    private final int antiguedad;
    private final String nombre;
    private final Cargo cargo;
    private final Origen planetaOrigen;
    private int cantidadConsejos;

    public Tripulante(int id, int antiguedad, String nombre, Cargo cargo, Origen planetaOrigen) {
        if (antiguedad < 0) {
            throw new IllegalArgumentException("La antiguedad no puede ser negativa");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(...);
        }
        if (cargo == null) {
            throw new IllegalArgumentException(...);
        }
        if (planetaOrigen == null) {
            throw new IllegalArgumentException(...);
        }

        this.id = id;
        this.antiguedad = antiguedad;
        this.nombre = nombre;
        this.cargo = cargo;
        this.planetaOrigen = planetaOrigen;
        this.cantidadConsejos=0;
    }

    public Liquidacion liquidarHaberes(){
        Liquidacion liquidacion = new SueldoBase(this.nombre, this.cargo);
        liquidacion = new Antiguedad(liquidacion,this.cargo,this.antiguedad);
        liquidacion = new SubsidioOrigen(liquidacion,this.planetaOrigen);
        if (this.cargo.equals(Cargo.CONSEJERO))
            liquidacion = new AdicionalConsejeros(liquidacion,this.cantidadConsejos);
        return liquidacion;
    }

    public void registrarConsejo(){
        if (this.cargo.equals(Cargo.CONSEJERO))
            this.cantidadConsejos++;
    }

    public int getCantidadConsejos() {return cantidadConsejos;}

    public int getId() {
        return id;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public String getNombre() {
        return nombre;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Origen getPlanetaOrigen() {
        return planetaOrigen;
    }

    @Override
    public String toString() {
        return nombre + " " + cargo
                + " Planeta de Origen: " + planetaOrigen
                + " Antiguedad: " + antiguedad;
    }
}
