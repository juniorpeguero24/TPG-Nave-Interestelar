package paquete.motorwarp;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        estado = new Disponible();
    }

    void cambiarEstado(EstadoWarp estado){
        this.estado = estado;
    }

    public String getEstado(){
        return this.estado.nombre();
    }

    public void prepararSalto(){
        this.estado.prepararSalto(this);
    }

    public void iniciarWarp(){
        this.estado.iniciarWarp(this);
    }

    public void finalizarWarp(){
        this.estado.finalizarWarp(this);
    }

    public void finalizarEnfriamiento(){
        this.estado.finalizarEnfriamiento(this);
    }

}
