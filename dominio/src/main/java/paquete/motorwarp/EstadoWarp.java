package paquete.motorwarp;

public interface EstadoWarp {
        String nombre();
        void prepararSalto();
        void iniciarWarp();
        void finalizarWarp();
        void finalizarEnfriamiento();
}
