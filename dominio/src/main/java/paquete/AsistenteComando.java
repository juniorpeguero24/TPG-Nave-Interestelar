package paquete;

import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class AsistenteComando {
    private final Nave nave;

    public AsistenteComando(Nave nave){
        this.nave = nave;
    }


    // ----- BITACORA ----- //

    public void registrarEvento(Evento evento){
        if (evento != null)
            nave.registrarEvento(evento);
    }

    public String generarInformeBitacora() {
        return nave.generarInformeBitacora();
    }


    // ----- MOTOR WARP ----- //

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

    public void consumirCombustible(int cantidad)
            throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        this.nave.consumirCombustible(cantidad);
        this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se consumieron " + cantidad + " unidades de combustible"));
    }

    public void consumirEnergia(int cantidad)
            throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
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
        this.iniciarWarp();
        this.finalizarWarp();
    }
}
