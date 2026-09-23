package paquete;

public class Tripulante {
    private int id;
    private int antiguedad;
    private String nombre;
    private Cargo cargo;
    private Origen planetaOrigen;

    public Tripulante(int id, int antiguedad, String nombre, Cargo cargo, Origen planetaOrigen) {
        if (antiguedad < 0) {
            throw new IllegalArgumentException("La antiguedad no puede ser negativa");
        }

        this.id = id;
        this.antiguedad = antiguedad;
        this.nombre = nombre;
        this.cargo = cargo;
        this.planetaOrigen = planetaOrigen;
    }

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
