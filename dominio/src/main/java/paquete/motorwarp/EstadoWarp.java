package paquete.motorwarp;

public interface EstadoWarp {
        String nombre();
        void prepararSalto(MotorWarp motor);
        void iniciarWarp(MotorWarp motor);
        void finalizarWarp(MotorWarp motor);
        void finalizarEnfriamiento(MotorWarp motor);
}
