package modelo;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.bitacora.Bitacora;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.nave.Nave;

public class AsistenteComando {
    private final Nave nave;
    private final Bitacora bitacora;

    public AsistenteComando(Nave nave){
        this.nave = nave;
        bitacora = new Bitacora();
    }

    // ----- BITACORA ----- //

    public void registrarEvento(Evento evento) {
        bitacora.registrarEvento(evento);
    }
    
    public String generarInformeBitacora() {
        return bitacora.toString();
    }

    // ----- MOTOR WARP ----- //

    public void prepararSalto(){
        Evento evento = nave.prepararSalto();
        this.registrarEvento(evento);
    }

    public void saltar(){
        Evento evento = nave.saltar();
        this.registrarEvento(evento);
    }

    public void enfriar(){
        Evento evento = nave.enfriar();
        this.registrarEvento(evento);
    }

    // ----- RECURSOS ----- //
    
    public void cargarCombustible(int cantidad) {
        try {
            nave.cargarCombustible(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de combustible"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
    }
    
    public void cargarEnergia(int cantidad) {
        try {
            nave.cargarEnergia(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de energia"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
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

    public void verificarDisponibilidadRecursos(int combustible, int energia, int desgaste)
            throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        this.nave.verificarDisponibilidadRecursos(combustible, energia, desgaste);
    }

// ----- CONSUMO DE RECURSOS DURANTE LA MISIÓN ----- //

    public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        this.nave.consumirCombustible(cantidad);
        this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se consumieron " + cantidad + " unidades de combustible"));
    }

    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        this.nave.consumirEnergia(cantidad);
        this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se consumieron " + cantidad + " unidades de energía"));
    }

    public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        this.nave.aumentarDesgaste(cantidad);
    }

    public void verificarDisponibilidadParaMision(int combustibleNecesario, int energiaNecesaria,int desgasteGenerado) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        if (!this.estaDisponibleParaSalto())
            throw new OperacionRecursoInvalidaExcepcion("\n[ERROR] Operacion Recurso Invalida." +
                    "\nLa nave no esta disponible para iniciar la mision." +
                    "\n Motor en estado: "+this.nave.obtenerEstadoActualMotor());
        this.nave.verificarDisponibilidadRecursos(combustibleNecesario,energiaNecesaria,desgasteGenerado);
    }

    public void ordenarSaltoWarp() {
        this.prepararSalto();
        this.saltar();
        this.enfriar();
    }
}
