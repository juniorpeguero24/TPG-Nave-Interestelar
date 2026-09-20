package paquete.bitacora;

public class EventoMotorWarp extends Evento {

    private String descripcion;

    public EventoMotorWarp(String descripcion) {
        super();
        this.descripcion = descripcion;
        }

    @Override
    public String toString() {
            return getFecha().format(FORMATO_FECHA) + " - MOTOR WARP: " + descripcion + "\n";
    }

}
