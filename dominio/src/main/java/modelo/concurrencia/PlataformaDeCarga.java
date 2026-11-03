package modelo.concurrencia;

import observer.ObservadorPlataforma;

import java.util.ArrayList;
import java.util.List;


public class PlataformaDeCarga  {
    private final int capacidadMaxima;
    private int cantBaterias;
    private final List<ObservadorPlataforma> observadores = new ArrayList<>();

    public PlataformaDeCarga(int capacidadMaxima, int cantBateriasInicial) {
        this.capacidadMaxima = capacidadMaxima;
        this.cantBaterias = cantBateriasInicial;
    }

    public PlataformaDeCarga() {
        this(5, 0);
    }

    public synchronized void depositarBateria(String idGeneradora) throws InterruptedException {
        while (this.cantBaterias >= this.capacidadMaxima) {
            String msj = ("[" + idGeneradora + "] esperando a que se libere espacio en la Plataforma.");
            notificarObservadores(msj);
            Thread.sleep(2000);
            wait();
        }
        this.cantBaterias++;
        String msj = ("Batería producida por [" + idGeneradora + "]. Baterías en plataforma: " + this.cantBaterias);
        notificarObservadores(msj);
        Thread.sleep(2000);
        notifyAll();
    }

    public synchronized void retirarBateria(String idNave) throws InterruptedException {
        while (this.cantBaterias == 0) {
            String msj = ("[" + idNave + "] esperando por falta de baterías en la Plataforma.");
            notificarObservadores(msj);
            Thread.sleep(2000);
            wait();
        }
        this.cantBaterias--;
        String msj = ("Batería retirada y utilizada por [" + idNave + "]. Baterías en plataforma: " + this.cantBaterias);
        notificarObservadores(msj);
        Thread.sleep(2000);
        notifyAll();
    }

    public void añadirObservador(ObservadorPlataforma observador) {
        observadores.add(observador);
    }

    public void quitarObservador(ObservadorPlataforma observador) {
        observadores.remove(observador);
    }

    public void notificarObservadores(String msj) {
        for (ObservadorPlataforma observador : observadores) {
            observador.actualizar(msj);
        }
    }

    public synchronized int getCantBaterias() {
        return this.cantBaterias;
    }

    public int getCapacidadMaxima() {
        return this.capacidadMaxima;
    }

}