package modelo.concurrencia;

public class PruebaConcurrencia {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("INICIANDO VISOR DEL UNIVERSO PARALELO");

        PlataformaDeCarga plataforma = new PlataformaDeCarga(5, 0);

        Thread g1 = new Thread(new GeneradoraDeEnergia("Gen-Alfa", plataforma, 1000));
        Thread g2 = new Thread(new GeneradoraDeEnergia("Gen-Beta", plataforma, 1200));
        g1.setDaemon(true);
        g2.setDaemon(true);
        g1.start();
        g2.start();

        Thread n1 = new Thread(new NaveConsumidora("Nave-Explorer-1", plataforma, 1500));
        Thread n2 = new Thread(new NaveConsumidora("Nave-Caza-2", plataforma, 2000));
        n1.setDaemon(true);
        n2.setDaemon(true);
        n1.start();
        n2.start();

        Thread.sleep(5000);

        System.out.println("\n[EVENTO]: ¡Ingreso una nueva Nave al universo paralelo!\n");
        Thread n3 = new Thread(new NaveConsumidora("Nave-Refuerzo-3", plataforma, 1000));
        n3.setDaemon(true);
        n3.start();

        Thread.sleep(4000);

        System.out.println("\n[EVENTO]: ¡Ingreso una nueva Generadora al universo paralelo!\n");
        Thread g3 = new Thread(new GeneradoraDeEnergia("Gen-Gamma", plataforma, 800));
        g3.setDaemon(true);
        g3.start();

        Thread.sleep(6000);

        System.out.println("\nFIN DE LA SIMULACIÓN DE PRUEBA");
    }
}