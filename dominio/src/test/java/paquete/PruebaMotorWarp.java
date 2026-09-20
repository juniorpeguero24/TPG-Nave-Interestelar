package paquete;

/// Prueba de cambios de estado validos e invalidos del motor Warp


public class PruebaMotorWarp {
    public static void main(String[] args) {
        Nave nave = new Nave(2, "ABCD", "carguero", 3000);

        nave.finalizarWarp();
        nave.finalizarEnfriamiento();
        nave.iniciarWarp();
        nave.prepararSalto();

        nave.iniciarWarp();
        nave.finalizarWarp();
        nave.finalizarEnfriamiento();

        System.out.println(nave.getBitacora());
    }
}
