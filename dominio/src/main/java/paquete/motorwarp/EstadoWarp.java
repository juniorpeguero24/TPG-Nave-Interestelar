package paquete.motorwarp;

import paquete.bitacora.Evento;

public interface EstadoWarp {
        Evento prepararSalto();
        Evento saltar();
        Evento enfriar();
}
