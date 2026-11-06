package modelo.nave;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;

import modelo.bitacora.Bitacora;
import modelo.excepciones.*;
import modelo.haberes.Liquidacion;
import modelo.tripulacion.Tripulante;
import java.util.ArrayList;

import modelo.mision.InformeMision;

import modelo.motorwarp.ResultadoSalto;
import modelo.motorwarp.TipoResultadoSalto;

import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

import modelo.universo.ElementoDelUniverso;
import modelo.universo.Ruta;

import observer.ObservadorBitacora;
import observer.ObservadorMision;
import observer.ObservadorNave;


public class AsistenteComando {
    private final Nave nave;
    private Bitacora bitacora;
    private final ArrayList<InformeMision> historialInformes = new ArrayList<>();
    private final ArrayList<ObservadorMision> observadores = new ArrayList<>();

    public AsistenteComando(Nave nave){
        this.nave = nave;
        this.bitacora = new Bitacora();
    }

    // ----- NAVE Y TRIPULACION ----- //

    public Nave getNave() {
        return nave;
    }

    public String getNombreNave() {
        return nave.getNombre();
    }
    public String getTipo() {return nave.getTipo();}

    public String obtenerEstadoNaveActual() {
        return nave.getEstadoActual();
    }

    public void agregarTripulante(Tripulante tripulante) throws CapitanExistenteException, TripulanteRepetidoException, TripulanteInvalidoException {
        nave.agregarTripulante(tripulante);
    }

    public boolean tieneTripulacionValida() {
        return nave.tieneTripulacionValida();
    }

    public ArrayList<Tripulante> getTripulantes() {
        return nave.getTripulantes();
    }

    public ArrayList<Liquidacion> liquidarHaberesTripulacion() {
        return nave.liquidarHaberesTripulacion();
    }

    public void registrarInforme(InformeMision informe) {
        historialInformes.add(informe);
        notificarObservadores();
    }

    public ArrayList<InformeMision> getHistorialInformes() {
        return new ArrayList<>(historialInformes);
    }

    // ----- BITACORA ----- //

    public Bitacora getBitacora() {
        return bitacora;
    }

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

    public ResultadoSalto saltar() throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        try {
            ResultadoSalto resultado = nave.saltar();
            registrarResultadoSalto(resultado);
            return resultado;
        } catch (RecursoInsuficienteExcepcion | OperacionRecursoInvalidaExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }
    
    public ResultadoSalto saltarWarp() throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        try {
            ResultadoSalto resultado = nave.saltarWarp();
            registrarResultadoSalto(resultado);
            return resultado;
        } catch (RecursoInsuficienteExcepcion | OperacionRecursoInvalidaExcepcion e) {
            registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
            throw e;
        }
    }
    
    private void registrarResultadoSalto(ResultadoSalto resultado) {
        TipoEvento tipoEvento = (resultado.getTipo() == TipoResultadoSalto.INVALIDO) ? TipoEvento.ERROR : TipoEvento.MOTOR_WARP;
        registrarEvento(new Evento(tipoEvento,resultado.getDescripcion()));
    }
    
    public boolean puedeSaltarWarp() {
        return nave.puedeSaltarWarp();
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
    
    public void agregarGemas(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        nave.agregarGemas(cantidad);
        registrarEvento(new Evento(TipoEvento.RECURSOS, "Se recolectaron " + cantidad + " gemas"));
    }

    // ----- CONSULTAS DE ESTADO Y DISPONIBILIDAD ----- //
    public boolean naveDestruida() {
        return nave.estaDestruida();
    }
 
    public boolean estaDisponibleParaSalto() {
        return "Disponible".equalsIgnoreCase(this.nave.getEstadoMotorWarp());
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
    
    // Acciones delegadas a la nave durante la mision
    public Ruta usarRadarLargoAlcance(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        return nave.usarRadarLargoAlcance(origen, destino);
    }
    
    public ElementoDelUniverso[][] usarRadarCortoAlcance(Tablero tablero) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        return nave.usarRadarCortoAlcance(tablero);
    }
    
    public Ruta recalcularRuta(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        return nave.recalcularRuta(origen, destino);
    }

    public void interactuarCon(ElementoDelUniverso elemento) {
        elemento.interactuarCon(nave);
        registrarEvento(new Evento(TipoEvento.MISION, "Interaccion con " + elemento));
    }


    //OBSERVER INFORMES

    public void agregarObservador(ObservadorMision observador) {
        this.observadores.add(observador);
    }

    public void quitarObservador(ObservadorMision observador) {
        this.observadores.remove(observador);
    }

    public void notificarObservadores() {
        for (ObservadorMision observador : observadores) {
            observador.actualizarInformes(this.historialInformes);
        }
    }
}
