package modelo.tripulacion;

import modelo.excepciones.TripulanteInvalidoException;
import modelo.haberes.AdicionalConsejeros;
import modelo.haberes.Antiguedad;
import modelo.haberes.Liquidacion;
import modelo.haberes.SubsidioOrigen;
import modelo.haberes.SueldoBase;

public class Tripulante {
    private static int identificadores = 0;
    private final int id;
    private final int antiguedad;
    private final String nombre;
    private final Cargo cargo;
    private final Origen planetaOrigen;
    private int cantidadConsejos;

    public Tripulante(int antiguedad, String nombre, Cargo cargo, Origen planetaOrigen) throws TripulanteInvalidoException {
        if (antiguedad < 0) {
            throw new TripulanteInvalidoException("La antiguedad no puede ser negativa");
        }
        if (antiguedad > 100) {
            throw new TripulanteInvalidoException("La antiguedad no es valida");
        }
        if (nombre == null || nombre.isBlank() || nombre.length() < 3 || nombre.length() > 15) {
            throw new TripulanteInvalidoException("Nombre invalido");
        }
        if (cargo == null) {
            throw new TripulanteInvalidoException("El cargo no puede ser nulo");
        }
        if (planetaOrigen == null) {
            throw new TripulanteInvalidoException("El origen no puede ser nulo");
        }

        this.id = identificadores++;
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

    public void registrarConsejo() throws TripulanteInvalidoException {
        if (this.cargo.equals(Cargo.CONSEJERO))
            this.cantidadConsejos++;
        else
            throw new TripulanteInvalidoException("El tripulante no es consejero");
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tripulante)) {
            return false;
        }
        Tripulante tripulante = (Tripulante) o;
        return id == tripulante.getId() &&
            cargo == tripulante.getCargo() &&
            planetaOrigen == tripulante.getPlanetaOrigen() &&
            nombre.equals(tripulante.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(id);
        hash = 31 * hash + this.cargo.hashCode();
        hash = 17 * hash + this.nombre.hashCode();
        hash = 7 * hash + this.planetaOrigen.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        String consejos = "";
        if (this.cargo.equals(Cargo.CONSEJERO)){
            consejos = " Consejos: " + this.cantidadConsejos;
        }
        return nombre + " - " + cargo
                + " Planeta de Origen: " + planetaOrigen
                + " Antiguedad: " + antiguedad + consejos;
    }
}
