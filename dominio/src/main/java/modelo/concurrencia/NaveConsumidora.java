package modelo.concurrencia;

import modelo.excepciones.NaveInvalidaException;
import modelo.nave.Nave;

public class NaveConsumidora implements Runnable {
    private final String id;
    private final PlataformaDeCarga plataforma;
    private final int tiempoConsumoMs;
    private volatile boolean enEjecucion = true;

    public NaveConsumidora(String id, PlataformaDeCarga plataforma, int tiempoConsumoMs) throws NaveInvalidaException {
        if (id.isBlank() || tiempoConsumoMs <= 0) {
            throw new NaveInvalidaException("No se pudo crear la nave");
        }

        this.id = id;
        this.plataforma = plataforma;
        this.tiempoConsumoMs = tiempoConsumoMs;
    }

    @Override
    public void run() {
        try {
            while (enEjecucion) {
                plataforma.retirarBateria(this.id);
                Thread.sleep(tiempoConsumoMs);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void detener() {
        this.enEjecucion = false;
    }

    public String getId() {
        return id;
    }
}