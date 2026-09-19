package paquete;

/// Prueba de cambios de estado validos e invalidos del motor Warp

import paquete.motorwarp.MotorWarp;

public class PruebaMotorWarp {
    public static void main(String[] args) {
        MotorWarp motor = new MotorWarp();

        System.out.println(motor.getEstado());

        //invalidos
        motor.iniciarWarp();
        motor.finalizarWarp();
        motor.finalizarEnfriamiento();
        //valido
        motor.prepararSalto();

        System.out.println(motor.getEstado());

        //invalidos
        motor.finalizarWarp();
        motor.finalizarEnfriamiento();
        motor.prepararSalto();
        //valido
        motor.iniciarWarp();

        System.out.println(motor.getEstado());

        //invalidos
        motor.iniciarWarp();
        motor.finalizarEnfriamiento();
        motor.prepararSalto();
        //valido
        motor.finalizarWarp();

        System.out.println(motor.getEstado());

        //invalidos
        motor.iniciarWarp();
        motor.prepararSalto();
        motor.finalizarWarp();
        //valido
        motor.finalizarEnfriamiento();

        System.out.println(motor.getEstado());
    }
}
