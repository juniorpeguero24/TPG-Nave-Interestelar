package modelo.concurrencia;

public class GeneradoraDeEnergia implements Runnable {
    private final String id;
    private final PlataformaDeCarga plataforma;
    private final int tiempoProduccionMs;
    private volatile boolean enEjecucion = true;

    public GeneradoraDeEnergia(String id, PlataformaDeCarga plataforma, int tiempoProduccionMs) {
        this.id = id;
        this.plataforma = plataforma;
        this.tiempoProduccionMs = tiempoProduccionMs;
    }

    @Override
    public void run() {
        try {
            while (enEjecucion) {
                Thread.sleep(tiempoProduccionMs);
                plataforma.depositarBateria(this.id);
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