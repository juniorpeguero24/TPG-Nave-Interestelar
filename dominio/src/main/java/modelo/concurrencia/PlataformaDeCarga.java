package modelo.concurrencia;

public class PlataformaDeCarga {
    private final int capacidadMaxima;
    private int cantBaterias;

    public PlataformaDeCarga(int capacidadMaxima, int cantBateriasInicial) {
        this.capacidadMaxima = capacidadMaxima;
        this.cantBaterias = cantBateriasInicial;
    }

    public PlataformaDeCarga() {
        this(5, 0);
    }

    public synchronized void depositarBateria(String idGeneradora) throws InterruptedException {
        while (this.cantBaterias >= this.capacidadMaxima) {
            System.out.println("[" + idGeneradora + "] esperando a que se libere espacio en la Plataforma.");
            wait();
        }
        this.cantBaterias++;
        System.out.println("Batería producida por [" + idGeneradora + "]. Baterías en plataforma: " + this.cantBaterias);
        notifyAll();
    }

    public synchronized void retirarBateria(String idNave) throws InterruptedException {
        while (this.cantBaterias == 0) {
            System.out.println("[" + idNave + "] esperando por falta de baterías en la Plataforma.");
            wait();
        }
        this.cantBaterias--;
        System.out.println("Batería retirada y utilizada por [" + idNave + "]. Baterías en plataforma: " + this.cantBaterias);
        notifyAll();
    }

    public synchronized int getCantBaterias() {
        return this.cantBaterias;
    }

    public int getCapacidadMaxima() {
        return this.capacidadMaxima;
    }
}