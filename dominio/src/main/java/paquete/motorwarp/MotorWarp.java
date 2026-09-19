package paquete.motorwarp;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        estado = new Disponible(this);
    }

    void cambiarEstado(EstadoWarp estado){
        this.estado = estado;
    }

    public String getEstado(){
        return this.estado.nombre();
    }

    public void prepararSalto(){
        this.estado.prepararSalto();
    }

    public void iniciarWarp(){
        this.estado.iniciarWarp();
    }

    public void finalizarWarp(){
        this.estado.finalizarWarp();
    }

    public void finalizarEnfriamiento(){
        this.estado.finalizarEnfriamiento();
    }

}
