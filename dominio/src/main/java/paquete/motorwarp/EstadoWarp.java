package paquete.motorwarp;

import paquete.bitacora.Evento;

public interface EstadoWarp {
        String nombre();
        Evento prepararSalto();
        Evento iniciarWarp();
        Evento finalizarWarp();
        Evento finalizarEnfriamiento();
}
