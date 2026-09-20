package paquete;

import paquete.bitacora.Bitacora;
import paquete.bitacora.Evento;
import paquete.motorwarp.MotorWarp;

public class AsistenteComando {

    private final Bitacora bitacora;
    private final MotorWarp motorWarp;

    public AsistenteComando(MotorWarp motorWarp, Bitacora bitacora){
        this.motorWarp = motorWarp;
        this.bitacora = bitacora;
    }


    // ----- BITACORA ----- //

    private void registrarEvento(Evento evento){
        this.bitacora.registraEvento(evento);
    }

    public String generarInformeBitacora() {
        return bitacora.toString();
    }


    // ----- MOTOR WARP ----- //

    public void prepararSalto(){
        Evento evento = this.motorWarp.prepararSalto();
        this.registrarEvento(evento);
    }

    public void iniciarWarp(){
        Evento evento = this.motorWarp.iniciarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarWarp(){
        Evento evento = this.motorWarp.finalizarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarEnfriamiento(){
        Evento evento = this.motorWarp.finalizarEnfriamiento();
        this.registrarEvento(evento);
    }


}
