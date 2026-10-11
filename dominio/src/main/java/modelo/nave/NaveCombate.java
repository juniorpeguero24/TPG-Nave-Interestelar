package modelo.nave;

public class NaveCombate extends Nave {
    public NaveCombate(String nombre) {
        super(nombre, 80, 100);
    }

    @Override
    public String getTipo() {
        return "combate";
    }
}
