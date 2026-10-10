package modelo.motorwarp;

import modelo.bitacora.Evento;

public interface EstadoWarp {
        Evento prepararSalto();
        Evento saltar();
        Evento enfriar();
}
