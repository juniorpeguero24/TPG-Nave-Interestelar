package modelo;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.mision.*;
import modelo.haberes.Liquidacion;
import modelo.nave.*;
import modelo.tripulacion.*;

public class Prueba {
    public static void main(String[] args) {
        Universo universo = crearUniversoBase();
        AsistenteComando asistente = universo.seleccionarNave("Combat-01");
        crearTripulacionBase(asistente);

        probarHaberes(asistente);
        probarMisiones(asistente);
        probarRecursosInsuficientes(asistente);
        probarMotorWarp(asistente);
        probarContratoInvalido(asistente);
        probarMantenimiento(asistente);

        System.out.println(asistente.generarInformeBitacora());
    }

    // Creo un universo base para las pruebas
    private static Universo crearUniversoBase() {
        Universo universo = new Universo();

        Nave exploradora = NaveFactory.crearNave("Explorer-01", "exploradora");
        Nave carguero = NaveFactory.crearNave("Cargo-01", "carguero");
        Nave combate = NaveFactory.crearNave("Combat-01", "combate");

        universo.registrarAsistente(new AsistenteComando(exploradora));
        universo.registrarAsistente(new AsistenteComando(carguero));
        universo.registrarAsistente(new AsistenteComando(combate));

        verificar("Factory creo una exploradora", exploradora instanceof NaveExploradora);
        verificar("Factory creo un carguero", carguero instanceof NaveCarguero);
        verificar("Factory creo una nave de combate", combate instanceof NaveCombate);
        verificar("Se registraron las tres naves", universo.getCantidadAsistentes() == 3);

        return universo;
    }

    private static void crearTripulacionBase(AsistenteComando asistente) {
        asistente.agregarTripulante(
                new Tripulante(
                        1, 5, "Capitan",
                        Cargo.CAPITAN,
                        Origen.TERRICOLA
                )
        );
        asistente.agregarTripulante(
                new Tripulante(
                        2, 2, "Tripulante 2",
                        Cargo.TENIENTE,
                        Origen.VULCANO
                )
        );
        asistente.agregarTripulante(
                new Tripulante(
                        3, 1, "Tripulante 3",
                        Cargo.ALFEREZ,
                        Origen.MARCIANO
                )
        );

        Tripulante consejero = new Tripulante(4, 3, "Tripulante 4", Cargo.CONSEJERO, Origen.VULCANO);
        asistente.agregarTripulante(consejero);

        consejero.registrarConsejo();
        consejero.registrarConsejo();

        asistente.agregarTripulante(
                new Tripulante(
                        5, 1, "Tripulante 5",
                        Cargo.TENIENTE,
                        Origen.VULCANO
                )
        );
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion)
            System.out.println("[OK] " + descripcion + "\n");
        else
            System.out.println("[ERROR] " + descripcion + "\n");
    }

    // Muestra los haberes para los cuatro cargos y los tres origenes
    private static void probarHaberes(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA HABERES =====");

        for(Liquidacion liquidacion:asistente.liquidarHaberesTripulacion()) {
            System.out.println(liquidacion.obtenerDetalle());
            System.out.println("TOTAL: " + liquidacion.calcularTotal() + " PG\n");
        }
    }

    // Realiza las tres misiones, muestra sus respectivos informes y el informe general de la Bitacora
    private static void probarMisiones(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA MISIONES =====");

        Mision m1 = new Mision01();
        InformeMision informe1 = m1.realizarMision(asistente);
        System.out.println(informe1);
        verificar("M-01 genero un informe", informe1 != null);

        Mision m2 = new Mision02();
        InformeMision informe2 = m2.realizarMision(asistente);
        System.out.println(informe2);
        verificar("M-02 genero un informe", informe2 != null);

        Mision m3 = new Mision03();
        InformeMision informe3 = m3.realizarMision(asistente);
        System.out.println(informe3);
        verificar("M-03 genero un informe", informe3 != null);
    }

    // Valida un caso de intento de realizar una mision con recursos insuficientes
    private static void probarRecursosInsuficientes(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA RECURSOS INSUFICIENTES =====");

        try {
            // Se drena combustible (valido)
            asistente.consumirCombustible(asistente.getCombustible());
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            System.out.println(e.getMessage());
        }

        int combustibleAntes = asistente.getCombustible();

        Mision mision = new Mision01();
        InformeMision informe = mision.realizarMision(asistente);
        System.out.println(informe);
        verificar("La mision fallo por recursos insuficientes", informe.getResultado() == ResultadoMision.FALLO);

        int combustibleDespues = asistente.getCombustible();
        verificar("El fallo no produjo consumo parcial", combustibleAntes == combustibleDespues);
    }

    // Muestra transiciones validas e invalidas del Motor Warp
    private static void probarMotorWarp(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA MOTOR WARP =====");

        String estadoInicial = asistente.getEstadoMotorWarp();
        verificar("El Motor Warp comienza Disponible", estadoInicial.equals("Disponible"));
        // Secuencia valida completa
        asistente.ordenarSaltoWarp();
        String estadoFinal = asistente.getEstadoMotorWarp();
        verificar("Al finalizar el salto vuelve a Disponible", estadoFinal.equals("Disponible"));

        // Transición invalida:
        // primero pasamos a Preparando salto (valido)
        asistente.prepararSalto();
        String antesInvalida = asistente.getEstadoMotorWarp();
        // Intentamos preparar nuevamente estando ya en Preparando salto
        asistente.prepararSalto();
        String despuesInvalida = asistente.getEstadoMotorWarp();
        verificar("La transicion invalida no modifica el estado", antesInvalida.equals(despuesInvalida));
    }

    // Pruebo un rechazo por una precondicion incumplida
    private static void probarContratoInvalido(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA CONTRATO INVALIDO =====");

        int combustibleAntes = asistente.getCombustible();

        try {
            asistente.cargarCombustible(500);
        } catch (OperacionRecursoInvalidaExcepcion e) {
            System.out.println(e.getMessage());
        }

        int combustibleDespues = asistente.getCombustible();

        verificar("Una carga invalida conserva el estado anterior", combustibleAntes == combustibleDespues);
    }

    private static void probarMantenimiento(AsistenteComando asistente) {
        System.out.println("\n===== PRUEBA MANTENIMIENTO =====");

        try {
            asistente.aumentarDesgaste(80);
        } catch (OperacionRecursoInvalidaExcepcion e) {
            System.out.println(e.getMessage());
            return;
        }

        verificar("Con desgaste 80 requiere mantenimiento", asistente.requiereMantenimiento());
        asistente.realizarMantenimiento();
        verificar("El mantenimiento deja el desgaste en 0",asistente.getDesgaste() == 0);
        verificar("Luego del mantenimiento deja de requerirlo", !asistente.requiereMantenimiento());
    }
}