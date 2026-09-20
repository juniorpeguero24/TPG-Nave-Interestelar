package paquete.bitacora;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Evento {

    protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime fecha;

     Evento() {
            fecha = LocalDateTime.now();
        }

     public LocalDateTime getFecha() {
            return fecha;
        }

     @Override
     public abstract String toString();
}
