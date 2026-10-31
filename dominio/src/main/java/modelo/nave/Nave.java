package modelo.nave;

import java.util.ArrayList;
import java.util.List;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.bitacora.Evento;
import modelo.motorwarp.MotorWarp;
import modelo.tripulacion.Tripulacion;
import modelo.mision.CostosMision;
import modelo.haberes.Liquidacion;
import modelo.tripulacion.Tripulante;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Posicion;
import modelo.universo.Ruta;
import observer.ObservadorNave;
import observer.ObservadorPlataforma;

public abstract class Nave implements ElementoDelUniverso {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;
    private Posicion posicion;
    private boolean destruida;
    private RadarLargoAlcance radarLargoAlcance;
    private final List<ObservadorNave> observadores = new ArrayList<>();

    public Nave( String nombre,int combustible,int energia, Posicion posicion) {
        this.nombre = nombre;
        this.motorWarp = new MotorWarp();
        this.tripulacion = new Tripulacion();
        this.recursos = new Recursos(combustible, energia);
        this.posicion = posicion;
        this.destruida = false;
        this.radarLargoAlcance = new RadarLargoAlcance();
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public Posicion getPosicion() {
        return posicion;
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
    public void agregarTripulante(Tripulante tripulante) {
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
    public Ruta usarRadarLargoAlcance(Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(CostosMision.COSTO_RADAR_LARGO);
        verificarDestruccion();
        return radarLargoAlcance.calcularRuta(posicion, destino);
    }
    
    public void avanzarA(Posicion nuevaPosicion) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        if (recursos.getCombustible() < CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE) {
            throw new RecursoInsuficienteExcepcion("Combustible insuficiente para avanzar");
        }
        if (recursos.getDesgaste() + CostosMision.COSTO_MOVIMIENTO_DESGASTE > 100) {
            throw new OperacionRecursoInvalidaExcepcion("El desgaste superaria el maximo permitido");
        }
        recursos.consumirCombustible(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE);
        recursos.aumentarDesgaste(CostosMision.COSTO_MOVIMIENTO_DESGASTE);
        posicion = nuevaPosicion;
        verificarDestruccion();
    }
    
    public Ruta recalcularRuta(Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(CostosMision.COSTO_RECALCULO);
        return radarLargoAlcance.calcularRuta(posicion, destino);
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
            observador.actualizar(this);
        }
    }

}
