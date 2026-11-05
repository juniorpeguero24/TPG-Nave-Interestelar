package modelo.tablero;

import observer.ObservadorTablero;

import java.util.ArrayList;

/** Base para objetos que notifican cambios a sus observadores. */
public abstract class Observable {
    /** Observadores que reciben las actualizaciones de este objeto. */
    private ArrayList<ObservadorTablero> observadores = new ArrayList<ObservadorTablero>();

    /**
     * Registra un observador para recibir las actualizaciones.
     * Un mismo observador no se registra más de una vez.
     * <p><b>Precondición:</b> {@code observador} no es {@code null}.</p>
     * @param observadorTablero observador por registrar
     */
    public void agregarObservador(ObservadorTablero observadorTablero) {
        if (!observadores.contains(observadorTablero)) {
            observadores.add(observadorTablero);
        }
    }

    /**
     * Quita un observador registrado. Si no estaba registrado, no hace nada.
     * @param observadorTablero observador por quitar
     */
    public void quitarObservador(ObservadorTablero observadorTablero) {
        observadores.remove(observadorTablero);
    }

    /**
     * Notifica a los observadores registrados que este objeto se actualizó.
     */
    public void notificarObservadores() {
        for (ObservadorTablero observadorTablero : observadores) {
            observadorTablero.actualizar();
        }
    }
}