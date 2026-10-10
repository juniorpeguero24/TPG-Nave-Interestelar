package modelo;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.mision.Mision;
import modelo.mision.Mision01;
import modelo.mision.Mision02;
import modelo.mision.Mision03;
import modelo.mision.InformeMision;
import modelo.mision.ResultadoMision;
import modelo.haberes.Liquidacion;
import modelo.nave;
import modelo.tripulacion;

public class Prueba {

    // Creo un universo base para las pruebas
    private static Universo crearUniversoBase() {
        Universo universo = new Universo();

        Nave exploradora = NaveFactory.crearNave("Explorer-01", "exploradora");
        Nave carguero = NaveFactory.crearNave("Cargo-01", "carguero");
        Nave combate = NaveFactory.crearNave("Combat-01", "combate");

        AsistenteComando asistenteExploradora = new AsistenteComando(exploradora);
        AsistenteComando asistenteCarguero = new AsistenteComando(carguero);
        AsistenteComando asistenteCombate = new AsistenteComando(combate);

        universo.registrarAsistente(asistenteExploradora);
        universo.registrarAsistente(asistenteCarguero);
        universo.registrarAsistente(asistenteCombate);

        return universo;
    }

    // Metodo para verificar todas las evidencias funcionales minimas
    private static void verificar(String descripcion, boolean condicion) {
        if (condicion)
            System.out.println("[OK] " + descripcion);
        else
            System.out.println("[ERROR] " + descripcion);
    }

    // Validacion de la creacion de los tres tipos de nave mediante Factory
    private static void probarFactory() {
        Universo universo = new Universo();

        Nave n1 = NaveFactory.crearNave("N1", "exploradora");
        Nave n2 = NaveFactory.crearNave("N2", "carguero");
        Nave n3 = NaveFactory.crearNave("N3", "combate");

        universo.registrarAsistente(new AsistenteComando(n1));
        universo.registrarAsistente(new AsistenteComando(n2));
        universo.registrarAsistente(new AsistenteComando(n3));

        verificar("Factory creo una exploradora", n1 instanceof NaveExploradora);
        verificar("Factory creo un carguero", n2 instanceof NaveCarguero);
        verificar("Factory creo una nave de combate", n3 instanceof NaveCombate);
        verificar("Se registraron las tres naves", universo.getCantidadAsistentes() == 3);
    }

    // Muestra la liquidacion de un tripulante
    private static void mostrarLiquidacion(Tripulante tripulante) {
        Liquidacion liquidacion = tripulante.liquidarHaberes();
        System.out.println("\n" + tripulante.getNombre());
        System.out.println(liquidacion.obtenerDetalle());
        System.out.println("TOTAL: " + liquidacion.calcularTotal());
    }

    // Muestra los haberes para los cuatro cargos y los tres origenes, incluyendo una combinacion de todos los decoradores con el consejero
    private static void probarHaberes() {
        System.out.println("\n===== PRUEBA HABERES =====");

        Tripulante capitan = new Tripulante(
            1, 2, "Capitan",
            Cargo.CAPITAN,
            Origen.TERRICOLA
        );
        Tripulante consejero = new Tripulante(
            2, 3, "Consejero",
            Cargo.CONSEJERO,
            Origen.VULCANO
        );
        Tripulante teniente = new Tripulante(
            3, 1, "Teniente",
            Cargo.TENIENTE,
            Origen.MARCIANO
        );
        Tripulante alferez = new Tripulante(
            4, 0, "Alferez",
            Cargo.ALFEREZ,
            Origen.TERRICOLA
        );

        consejero.registrarConsejo();
        consejero.registrarConsejo();

        mostrarLiquidacion(capitan);
        mostrarLiquidacion(consejero);
        mostrarLiquidacion(teniente);
        mostrarLiquidacion(alferez);
    }

    // Genera y devuelve un asistente con una nave de combate asociada con una tripulacion valida
    private static AsistenteComando crearAsistenteOperativo() {
        Universo universo = crearUniversoBase();
        AsistenteComando asistente = universo.seleccionarNave("Combat-01");

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
        asistente.agregarTripulante(
            new Tripulante(
                4, 3, "Tripulante 4",
                Cargo.CONSEJERO,
                Origen.TERRICOLA
            )
        );
        asistente.agregarTripulante(
            new Tripulante(
                5, 1, "Tripulante 5",
                Cargo.TENIENTE,
                Origen.VULCANO
            )
        );

        return asistente;
    }

    // Crea un asistente con una nave valida, realiza las tres misiones, muestra sus respectivos informes y el informe general de la Bitacora 
    private static void probarMisiones() {
        System.out.println("\n===== PRUEBA MISIONES =====");

        AsistenteComando asistente = crearAsistenteOperativo();

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

        System.out.println(asistente.generarInformeBitacora());
    }

    // Valida un caso de intento de realizar una mision con recursos insuficientes
    private static void probarRecursosInsuficientes() {
        System.out.println("\n===== PRUEBA RECURSOS INSUFICIENTES =====");

        AsistenteComando asistente = crearAsistenteOperativo();

        try {
            // La nave de combate empieza con 80. Dejamos solamente 2.
            asistente.consumirCombustible(78);
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
    private static void probarMotorWarp() {
        System.out.println("\n===== PRUEBA MOTOR WARP =====");

        AsistenteComando asistente = crearAsistenteOperativo();

        String estadoInicial = asistente.getEstadoMotorWarp();
        verificar("El Motor Warp comienza Disponible", estadoInicial.equals("Disponible"));
        // Secuencia válida completa
        asistente.ordenarSaltoWarp();
        String estadoFinal = asistente.getEstadoMotorWarp();
        verificar("Al finalizar el salto vuelve a Disponible", estadoFinal.equals("Disponible"));

        // Transición inválida:
        // primero pasamos válidamente a Preparando salto
        asistente.prepararSalto();
        String antesInvalida = asistente.getEstadoMotorWarp();
        // Intentamos preparar nuevamente estando ya en Preparando salto
        asistente.prepararSalto();
        String despuesInvalida = asistente.getEstadoMotorWarp();
        verificar("La transicion invalida no modifica el estado", antesInvalida.equals(despuesInvalida));

        System.out.println(asistente.generarInformeBitacora());
    }

    // Pruebo un rechazo por una precondicion incumplida
    private static void probarContratoInvalido() {
        System.out.println("\n===== PRUEBA CONTRATO INVALIDO =====");

        AsistenteComando asistente = crearAsistenteOperativo();

        int combustibleAntes = asistente.getCombustible();

        try {
            asistente.cargarCombustible(500);
        } catch (OperacionRecursoInvalidaExcepcion e) {
            System.out.println("Excepcion esperada: " + e.getMessage()
            );
        }

        int combustibleDespues = asistente.getCombustible();

        verificar("Una carga invalida conserva el estado anterior", combustibleAntes == combustibleDespues);
    }

    private static void probarMantenimiento() {
        System.out.println("\n===== PRUEBA MANTENIMIENTO =====");

        AsistenteComando asistente = crearAsistenteOperativo();

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

    public static void main(String[] args) {
        probarFactory();
        probarHaberes();
        probarMisiones();
        probarRecursosInsuficientes();
        probarMotorWarp();
        probarContratoInvalido();
        probarMantenimiento();
    }
}