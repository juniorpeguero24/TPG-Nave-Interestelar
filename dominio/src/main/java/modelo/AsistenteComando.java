package modelo;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;

import modelo.bitacora.Bitacora;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

public class AsistenteComando {
    private final Nave nave;
    private Bitacora bitacora;

    public AsistenteComando(Nave nave){
        this.nave = nave;
        this.bitacora = new Bitacora();
    }

    // ----- NAVE ----- //

    public Nave getNave() {
        return nave;
    }

    public String getNombreNave() {
        return nave.getNombre();
    }

    public String obtenerEstadoNaveActual() {
        return nave.obtenerEstadoActual();
    }

    public void agregarTripulante(Tripulante tripulante) {
        nave.agregarTripulante(tripulante);
    }

    public boolean tieneTripulacionValida() {
        return nave.tieneTripulacionValida();
    }

    // ----- BITACORA ----- //

    public void registrarEvento(Evento evento) {
        bitacora.registrarEvento(evento);
    }

    public String generarInformeBitacora() {
        return bitacora.toString();
    }

    // ----- MOTOR WARP ----- //

    public String getEstadoMotorWarp() {
        return nave.getEstadoMotorWarp();
    }

    public void prepararSalto(){
        Evento evento = nave.prepararSalto();
        this.registrarEvento(evento);
    }

    public void iniciarWarp(){
        Evento evento = nave.iniciarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarWarp(){
        Evento evento = nave.finalizarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarEnfriamiento(){
        Evento evento = nave.finalizarEnfriamiento();
        this.registrarEvento(evento);
    }

    public void ordenarSaltoWarp() {
        this.prepararSalto();
        this.iniciarWarp();
        this.finalizarWarp();
    }

    // ----- RECURSOS ----- //

    public int getCombustible() {
        return nave.getCombustible();
    }

    public int getEnergia() {
        return nave.getEnergia();
    }

    public int getDesgaste() {
        return nave.getDesgaste();
    }

    public boolean requiereMantenimiento() {
        return nave.requiereMantenimiento();
    }

    public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        try {
            nave.cargarCombustible(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de combustible"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

    public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        try {
            nave.cargarEnergia(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de energia"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

    public void realizarMantenimiento() {
        nave.realizarMantenimiento();
        this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se realizo el mantenimiento de la nave"));
    }

    // ----- CONSULTAS DE ESTADO Y DISPONIBILIDAD ----- //

 
    public boolean estaDisponibleParaSalto() {
        return "Disponible".equalsIgnoreCase(this.nave.obtenerEstadoActualMotor());
    }

    public void verificarDisponibilidadRecursos(int combustible, int energia, int desgaste) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        try {
            this.nave.verificarDisponibilidadRecursos(combustible, energia, desgaste);
        }
        catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

// ----- CONSUMO DE RECURSOS DURANTE LA MISIÓN ----- //

    public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        try {
            this.nave.consumirCombustible(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se consumieron " + cantidad + " unidades de combustible"));
        }
        catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        try {
            this.nave.consumirEnergia(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se consumieron " + cantidad + " unidades de energía"));
        }
        catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

    public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        try {
            this.nave.aumentarDesgaste(cantidad);
        }
        catch (OperacionRecursoInvalidaExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

    public void verificarDisponibilidadParaMision(int combustibleNecesario, int energiaNecesaria,int desgasteGenerado) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        try {
            if (!this.estaDisponibleParaSalto())
                throw new OperacionRecursoInvalidaExcepcion("\n[ERROR] Operacion Recurso Invalida." +
                        "\nLa nave no esta disponible para iniciar la mision." +
                        "\n Motor en estado: "+this.nave.obtenerEstadoActualMotor());
            this.nave.verificarDisponibilidadRecursos(combustibleNecesario,energiaNecesaria,desgasteGenerado);
        }
        catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }

}
