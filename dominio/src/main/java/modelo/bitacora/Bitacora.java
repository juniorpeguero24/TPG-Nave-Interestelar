package modelo.bitacora;

import observer.ObservadorBitacora;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bitacora {
    private final ArrayList<Evento> eventos;
    private final ArrayList<ObservadorBitacora> observadores;

    public Bitacora(){

        eventos = new ArrayList<Evento>();
        observadores = new ArrayList<>();
    }

    public void registrarEvento(Evento evento){
        if (evento != null && evento.esValido()) {
            eventos.add(evento);
            notificarObservadores();
        }
    }

    public ArrayList<Evento> consultarEvento(LocalDateTime desde, LocalDateTime hasta){
        ArrayList<Evento> eventosConsultados = new ArrayList<>();

        for (Evento evento : eventos) {
            if (!evento.getFecha().isBefore(desde) && !evento.getFecha().isAfter(hasta)) {
                eventosConsultados.add(evento);
            }
        }

        return eventosConsultados;
        }

    @Override
    public String toString(){
        return "BITACORA: \n" + eventos.toString();
    }

    // OBSERVER

    public void agregarObservador(ObservadorBitacora observador){
        observadores.add(observador);
    }

    public void quitarObservador(ObservadorBitacora observador){
        observadores.remove(observador);
    }

    public void notificarObservadores(){
        for (ObservadorBitacora observador : observadores) {
            observador.actualizarBitacora(this);
        }
    }
}
