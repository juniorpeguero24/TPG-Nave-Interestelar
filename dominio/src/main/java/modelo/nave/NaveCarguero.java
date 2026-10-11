package modelo.nave;

public class NaveCarguero extends Nave {
    public NaveCarguero(String nombre) {
        super(nombre, 100, 60);
    }

    @Override
    public String getTipo() {
        return "carguero";
    }
}
