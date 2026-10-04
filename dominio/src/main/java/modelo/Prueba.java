package modelo;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.haberes.Liquidacion;
import modelo.mision.Mision;
import modelo.mision.Mision01;
import modelo.mision.Mision02;
import modelo.mision.Mision03;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.tripulacion.Cargo;
import modelo.tripulacion.Origen;
import modelo.tripulacion.Tripulante;
import java.time.LocalDateTime;

public class Prueba {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("SIMULACION - NAVE INTERESTELAR");

        // Universo
        Universo universo = new Universo();

        // Creacion de las naves
        Nave nave1 = NaveFactory.crearNave("USS Discovery", "exploradora");
        Nave nave2 = NaveFactory.crearNave("Phoenix", "carguero");
        Nave nave3 = NaveFactory.crearNave("Crimson Eclipse", "combate");

        if (nave1 == null || nave2 == null || nave3 == null) {
            System.err.println("Error: no se pudieron crear las naves.");
            return;
        }

        // Cada nave tiene su asistente de comandos y se registra en el Universo
        universo.registrarAsistente(new AsistenteComando(nave1));

        // El Universo entrega la nave para operar
        AsistenteComando asistente1 = universo.seleccionarNaveParaOperar(0);
        System.out.println("Nave en operacion: " + asistente1.getNombreNave());
        System.out.println("Estado inicial:\n" + asistente1.getEstadoNave() + "\n");

        int combustibleAntes = asistente1.getCombustible();
        int energiaAntes = asistente1.getEnergia();
        int desgasteAntes = asistente1.getDesgaste();

        // Asignacion de tripulacion
        asistente1.agregarTripulante(new Tripulante(1,8,"Darth", Cargo.CAPITAN, Origen.TERRICOLA));
        asistente1.agregarTripulante(new Tripulante(2,1,"Pol",Cargo.ALFEREZ,Origen.MARCIANO));
        asistente1.agregarTripulante(new Tripulante(3,5,"Ellen",Cargo.TENIENTE,Origen.TERRICOLA));
        asistente1.agregarTripulante(new Tripulante(4,7,"Spock",Cargo.CONSEJERO,Origen.VULCANO));
        asistente1.agregarTripulante(new Tripulante(5,3,"Samus",Cargo.TENIENTE,Origen.TERRICOLA));

        if(!asistente1.naveTieneTripulacionValida()) {
            System.err.println("Error: la tripulacion no es valida.");
            return;
        }

        // Liquidacion haberes tripulacion
        for(Liquidacion l:asistente1.liquidarHaberesTripulacion()) {
            System.out.println(l.obtenerDetalle());
            System.out.println("Total: " + l.calcularTotal() + " PG\n");
        }

        // Ejecucion de Mision 01
        Mision mision = new Mision01();
        System.out.println("\n--- ENCOMENDANDO " + mision.getNombre() + " ---");
        LocalDateTime inicioMision = LocalDateTime.now();

        try {
            mision.realizarMision(asistente1);
            System.out.println("Mision 01 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 01: " + e.getMessage());
            asistente1.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
        LocalDateTime finMision = LocalDateTime.now();

        System.out.println("Estado tras Mision 01:\n" + asistente1.getEstadoNave() + "\n");

        System.out.println("REGISTROS BITACORA");
        for(Evento e:asistente1.getEventos(inicioMision, finMision))
            System.out.print(e.toString());

        int combustibleAhora = asistente1.getCombustible();
        int energiaAhora = asistente1.getEnergia();
        int desgasteAhora  = asistente1.getDesgaste();

        System.out.println(mision.getInforme(combustibleAntes - combustibleAhora,
                energiaAntes - energiaAhora,
                desgasteAhora - desgasteAntes,
                asistente1.obtenerEstadoActualMotor()));

        combustibleAntes = combustibleAhora;
        energiaAntes = energiaAhora;
        desgasteAntes = desgasteAhora;

        // Ejecucion de Mision 02 sobre la misma nave
        Thread.sleep(1000);
        mision = new Mision02();
        System.out.println("\n--- ENCOMENDANDO " + mision.getNombre() + " ---");
        inicioMision = LocalDateTime.now();

        try {
            mision.realizarMision(asistente1);
            System.out.println("Mision 02 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 02: " + e.getMessage());
            asistente1.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
        finMision = LocalDateTime.now();

        System.out.println("Estado tras Mision 02:\n" + asistente1.getEstadoNave() + "\n");

        System.out.println("REGISTROS BITACORA");
        for(Evento e:asistente1.getEventos(inicioMision, finMision))
            System.out.print(e.toString());

        combustibleAhora = asistente1.getCombustible();
        energiaAhora = asistente1.getEnergia();
        desgasteAhora  = asistente1.getDesgaste();

        System.out.println(mision.getInforme(combustibleAntes - combustibleAhora,
                energiaAntes - energiaAhora,
                desgasteAhora - desgasteAntes,
                asistente1.obtenerEstadoActualMotor()));

        combustibleAntes = combustibleAhora;
        energiaAntes = energiaAhora;
        desgasteAntes = desgasteAhora;

        // Ejecucion de Mision 03 sobre la misma nave
        Thread.sleep(1000);
        mision = new Mision03();
        System.out.println("\n--- ENCOMENDANDO " + mision.getNombre() + " ---");
        inicioMision = LocalDateTime.now();

        try {
            mision.realizarMision(asistente1);
            System.out.println("Mision 03 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 03: " + e.getMessage());
            asistente1.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
        finMision = LocalDateTime.now();

        System.out.println("Estado tras Mision 03:\n" + asistente1.getEstadoNave() + "\n");

        System.out.println("REGISTROS BITACORA");
        for(Evento e:asistente1.getEventos(inicioMision, finMision))
            System.out.print(e.toString());

        combustibleAhora = asistente1.getCombustible();
        energiaAhora = asistente1.getEnergia();
        desgasteAhora  = asistente1.getDesgaste();

        System.out.println(mision.getInforme(combustibleAntes - combustibleAhora,
                energiaAntes - energiaAhora,
                desgasteAhora - desgasteAntes,
                asistente1.obtenerEstadoActualMotor()) + "\n");

        combustibleAntes = combustibleAhora;
        energiaAntes = energiaAhora;
        desgasteAntes = desgasteAhora;


        // ESCENARIO DE FALLOS Y ERRORES !!
        Thread.sleep(1000);
        inicioMision = LocalDateTime.now();

        try {
            asistente1.consumirCombustible(80); // Cantidad de combustible a consumir invalida: queda por debajo del limite inferior !!
        } catch (RecursoInsuficienteExcepcion | OperacionRecursoInvalidaExcepcion e) {
            System.err.println("Error: no se pudo consumir combustible ("+e.getMessage()+")");
        }

        try {
            asistente1.consumirCombustible(40); // Se drena combustible antes de iniciar mision (valido)
            combustibleAntes = asistente1.getCombustible();
        } catch (RecursoInsuficienteExcepcion | OperacionRecursoInvalidaExcepcion e) {
            System.err.println("Error: no se pudo consumir combustible ("+e.getMessage()+")");
        }

        mision = new Mision01();
        System.out.println("--- ENCOMENDANDO " + mision.getNombre() + " ---");
        asistente1.enfriar(); // Transicion invalida !!

        try {
            mision.realizarMision(asistente1); // Combustible insuficiente !!
            System.out.println("Mision 01 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 01: " + e.getMessage());
            asistente1.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
        finMision = LocalDateTime.now();

        System.out.println("Estado tras Mision 01:\n" + asistente1.getEstadoNave() + "\n");

        System.out.println("REGISTROS BITACORA");
        for(Evento e:asistente1.getEventos(inicioMision, finMision))
            System.out.print(e.toString());

        combustibleAhora = asistente1.getCombustible();
        energiaAhora = asistente1.getEnergia();
        desgasteAhora  = asistente1.getDesgaste();

        System.out.println(mision.getInforme(combustibleAntes - combustibleAhora,
                energiaAntes - energiaAhora,
                desgasteAhora - desgasteAntes,
                asistente1.obtenerEstadoActualMotor()));
    }
}