package paquete;

public abstract class Tripulante {
    private int id, antiguedad;
    private String nombre, cargo, planetaOrigen;


    public Tripulante(int id, int antiguedad, String nombre, String cargo, String planetaOrigen) {
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

    public String getCargo() {
        return cargo;
    }

    public String getPlanetaOrigen() {
        return planetaOrigen;
    }


    @Override
    public String toString() {
        return nombre+" "+cargo+" "+" Planeta de Origen: "+planetaOrigen+" Antiguedad: "+antiguedad;
    }
}
