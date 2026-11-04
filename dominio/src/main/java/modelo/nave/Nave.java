package modelo.nave;

import java.util.ArrayList;
import java.util.List;

import modelo.excepciones.*;
import modelo.bitacora.Evento;
import modelo.motorwarp.MotorWarp;
import modelo.tripulacion.Tripulacion;
import modelo.mision.CostosMision;
import modelo.haberes.Liquidacion;

import modelo.tablero.Posicion;

import modelo.tablero.Tablero;

import modelo.tripulacion.Tripulante;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Ruta;
import observer.ObservadorNave;
import observer.ObservadorPlataforma;

public abstract class Nave implements ElementoDelUniverso {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;
    private boolean destruida;
    private RadarLargoAlcance radarLargoAlcance;
    private final List<ObservadorNave> observadores = new ArrayList<>();
    private RadarCortoAlcance radarCortoAlcance;


    public Nave( String nombre,int combustible,int energia) {
        this.nombre = nombre;
        this.motorWarp = new MotorWarp();
        this.tripulacion = new Tripulacion();
        this.recursos = new Recursos(combustible, energia);
        this.destruida = false;
        this.radarLargoAlcance = new RadarLargoAlcance();
        this.radarCortoAlcance = new RadarCortoAlcance();
    }

    public String getNombre() {
        return nombre;
    }
    
    public boolean estaDestruida() {
        return destruida;
    }

    public void destruir() {
        destruida = true;
    }
    
    public void verificarDestruccion() {
        if (recursos.getEnergia() == 0 || recursos.getCombustible() == 0 || recursos.getDesgaste() >= 100) {
            destruir();
        }
    }

    public abstract String getTipo();
    
    public String getEstadoActual() {
        return "\n  Combustible: " + recursos.getCombustible()
                + "\n  Energia: " + recursos.getEnergia()
                + "\n  Desgaste: " + recursos.getDesgaste()
                + "\n  Requiere mantenimiento: " + recursos.requiereMantenimiento()
                + "\n  Motor Warp: " + motorWarp.getEstado();
    }
    
    // Operaciones delegadas sobre Tripulacion
    public void agregarTripulante(Tripulante tripulante) throws CapitanExistenteException, TripulanteRepetidoException, TripulanteInvalidoException {
        this.tripulacion.agregarTripulante(tripulante);
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulacion.getTripulantes();
    }
    
    public boolean tieneTripulacionValida() {
        return tripulacion.esValida();
    }

    public ArrayList<Liquidacion> liquidarHaberesTripulacion() {
        return tripulacion.liquidarHaberes();
    }
    
    // Operaciones delegadas sobre Recursos 
    public int getCombustible() {
        return recursos.getCombustible();
    }

    public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.cargarCombustible(cantidad);
        notificarObservadores();
    }

    public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirCombustible(cantidad);
        notificarObservadores();
    }
    
    public int getEnergia() {
        return recursos.getEnergia();
    }
    
    public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.cargarEnergia(cantidad);
        notificarObservadores();
    }
    
    // gasto voluntario
    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(cantidad);
        notificarObservadores();
    }
    
    // gasto involuntario
    public void recibirDanioEnergia(int cantidad) {
        recursos.reducirEnergia(cantidad);
        verificarDestruccion();
        notificarObservadores();
    }
    
    public int getDesgaste() {
        return recursos.getDesgaste();
    }
    
    public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.aumentarDesgaste(cantidad);
        notificarObservadores();
    }
    
    public void realizarMantenimiento(){

        recursos.realizarMantenimiento();
        notificarObservadores();
    }
    
    public boolean requiereMantenimiento(){
        return recursos.requiereMantenimiento();
    }  
    
    public void verificarDisponibilidadRecursos(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        recursos.verificarDisponibilidadRecursos(combustibleNecesario,energiaNecesaria,desgasteGenerado);
    }
    
    // Devuelve cuanto espacio le queda para llenarse
    public int getEspacioCombustible() {
        return recursos.getEspacioCombustible();
    }

    public int getEspacioEnergia() {
        return recursos.getEspacioEnergia();
    }
    
    // Encuentros entre naves
    public abstract ResultadoEncuentro enfrentar(Nave otraNave);

    protected abstract ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora);

    protected abstract ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero);

    protected abstract ResultadoEncuentro enfrentarConCombate(NaveCombate combate);
    
    @Override
    public void interactuarCon(Nave nave) {
        ResultadoEncuentro resultado = nave.enfrentar(this);
        if (resultado == ResultadoEncuentro.PIERDE) {
            nave.destruir();
        }
    }
    
    // Operaciones delegadas sobre el Motor Warp
    public String getEstadoMotorWarp() {
        return motorWarp.getEstado();
    }

    public Evento prepararSalto(){
        Evento evento = motorWarp.prepararSalto();
        notificarObservadores();
        return evento;
    }

    public Evento saltar(){
        Evento evento = motorWarp.saltar();
        notificarObservadores();
        return evento;
    }

    public Evento enfriar(){
        Evento evento = motorWarp.enfriar();
        notificarObservadores();
        return evento;
    }
    
    // Metodos usados para las Misiones
    public Ruta usarRadarLargoAlcance(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(CostosMision.COSTO_RADAR_LARGO);
        notificarObservadores();
        verificarDestruccion();
        return radarLargoAlcance.calcularRuta(origen, destino);
    }
    
    public ElementoDelUniverso[][] usarRadarCortoAlcance(Tablero tablero) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(CostosMision.COSTO_RADAR_CORTO);
        notificarObservadores();
        verificarDestruccion();
        return radarCortoAlcance.escanear(tablero);
    }
    
    public void avanzar() throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.verificarDisponibilidadRecursos(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE, 0, CostosMision.COSTO_MOVIMIENTO_DESGASTE);
        recursos.consumirCombustible(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE);
        recursos.aumentarDesgaste(CostosMision.COSTO_MOVIMIENTO_DESGASTE);
        notificarObservadores();
        verificarDestruccion();
    }
    
    public Ruta recalcularRuta(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(CostosMision.COSTO_RECALCULO);
        notificarObservadores();
        return radarLargoAlcance.calcularRuta(origen, destino);
    }



    // OBSERVADORES

    public void añadirObservador(ObservadorNave observador) {
        observadores.add(observador);
    }

    public void quitarObservador(ObservadorNave observador) {

        observadores.remove(observador);
    }

    public void notificarObservadores() {
        for (ObservadorNave observador : observadores) {
            observador.actualizarNave(this);
        }
    }

}
