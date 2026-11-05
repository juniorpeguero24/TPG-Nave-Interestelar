package observer;

/** Recibe avisos cuando se actualiza el tablero observado. */
public interface ObservadorTablero {
    /** Ejecuta la actualización ante un cambio en el tablero. */
    void actualizar();
}