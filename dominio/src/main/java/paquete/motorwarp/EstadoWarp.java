package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

public interface EstadoWarp {
        String nombre();
        EventoMotorWarp prepararSalto();
        EventoMotorWarp iniciarWarp();
        EventoMotorWarp finalizarWarp();
        EventoMotorWarp finalizarEnfriamiento();
}
