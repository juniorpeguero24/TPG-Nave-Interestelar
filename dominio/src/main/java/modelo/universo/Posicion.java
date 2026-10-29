package modelo.universo;

public class Posicion {
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) { // se usa para verificar que dos elementos del universo esten en la misma casilla
        if (this == obj)
            return true;
        if (!(obj instanceof Posicion))
            return false;
        Posicion otra = (Posicion) obj;
        return x == otra.x && y == otra.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}