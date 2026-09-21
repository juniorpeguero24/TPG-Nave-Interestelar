package paquete.bitacora;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime fecha;
    private final TipoEvento tipo;
    private final String descripcion;

     public Evento(TipoEvento tipo, String descripcion) {

         fecha = LocalDateTime.now();
         this.tipo = tipo;
         this.descripcion = descripcion;

     }

     public LocalDateTime getFecha() {
            return fecha;
        }

     public TipoEvento getTipo(){
         return this.tipo;
     }

    @Override
    public String toString() {
        return getFecha().format(FORMATO_FECHA) + " " + tipo.toString() + ": " + descripcion + "\n";
    }
}
