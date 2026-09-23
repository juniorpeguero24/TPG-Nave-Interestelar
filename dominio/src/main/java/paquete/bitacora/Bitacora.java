package paquete.bitacora;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bitacora {
    private ArrayList<Evento> eventos;

    public Bitacora(){
        eventos = new ArrayList<Evento>();
    }

    public void registrarEvento(Evento evento){
        if (evento != null)
            eventos.add(evento);
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
        return "BITACORA: \n\n" + eventos.toString();
    }
}
