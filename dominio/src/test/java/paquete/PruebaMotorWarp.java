package paquete;

public class PruebaMotorWarp {
    public static void main(String[] args) {
        Nave nave = NaveFactory.crearNave("ABCD", "carguero");
        AsistenteComando asistente = nave.getAsistenteComando();

        // Órdenes inválidas desde el estado inicial Disponible.
        asistente.finalizarWarp();
        asistente.iniciarWarp();

        // Secuencia válida: Disponible -> PreparandoSalto -> EnWarp
        // -> Enfriamiento -> Disponible.
        asistente.prepararSalto();
        asistente.iniciarWarp();
        asistente.finalizarWarp();
        asistente.finalizarEnfriamiento();

        System.out.println(asistente.generarInformeBitacora());
    }
}
