package modelo;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.mision.Mision;
import modelo.mision.Mision01;
import modelo.mision.Mision02;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.tripulacion.Cargo;
import modelo.tripulacion.Origen;
import modelo.tripulacion.Tripulante;

public class Prueba {

    public static void main(String[] args) {

        System.out.println("SIMULACION - NAVE INTERESTELAR");

        // Universo
        Universo universo = new Universo();

        // Creacion de la nave
        Nave nave = NaveFactory.crearNave("USS Discovery", "exploradora");
        if (nave == null) {
            System.err.println("Error: no se pudo crear la nave.");
            return;
        }

        // Asignacion de tripulacion
        Tripulante comandante = new Tripulante(1,8,"Carlos", Cargo.CAPITAN, Origen.TERRICOLA);
        Tripulante navegante = new Tripulante(2,4,"Pol",Cargo.ALFEREZ,Origen.MARCIANO);
        nave.agregarTripulante(comandante);
        nave.agregarTripulante(navegante);

        // Cada nave tiene su asistente de comandos y se registra en el Universo
        AsistenteComando asistente = new AsistenteComando(nave);
        universo.registrarAsistente(asistente);

        // El Universo entrega la nave para operar
        AsistenteComando asistenteEnOperacion = universo.seleccionarNaveParaOperar(0);
        System.out.println("Nave en operacion: " + nave.getNombre());
        System.out.println("Estado inicial:\n" + nave.obtenerEstadoActual() + "\n");

        // Ejecucion de Mision 01

        System.out.println("--- ENCOMENDANDO MISION 01 ---");
        Mision mision1 = new Mision01();
        try {
            mision1.realizarMision(asistenteEnOperacion);
            System.out.println("Mision 01 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 01: " + e.getMessage());
            asistenteEnOperacion.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }

        System.out.println("Estado tras Mision 01:\n" + nave.obtenerEstadoActual() + "\n");

        // Ejecucion de Mision 02 sobre la misma nave

        System.out.println("--- ENCOMENDANDO MISION 02 ---");
        Mision mision2 = new Mision02();
        try {
            mision2.realizarMision(asistenteEnOperacion);
            System.out.println("Mision 02 finalizada.");
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println("Fallo al ejecutar Mision 02: " + e.getMessage());
            asistenteEnOperacion.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }

        System.out.println("Estado tras Mision 02:\n" + nave.obtenerEstadoActual() + "\n");


        // Informe de la Bitacora

        System.out.println("==================================================");
        System.out.println("INFORME DE BITACORA DEL ASISTENTE");
        System.out.println("==================================================");
        System.out.println(asistenteEnOperacion.generarInformeBitacora());
    }
}