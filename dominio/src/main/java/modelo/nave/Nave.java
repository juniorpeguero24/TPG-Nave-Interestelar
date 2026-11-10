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

import modelo.motorwarp.ResultadoSalto;
import modelo.motorwarp.TipoResultadoSalto;

import modelo.universo.ResultadoInteraccion;

public abstract class Nave implements ElementoDelUniverso {
    private String nombre;
    private Recursos recursos;
    private MotorWarp motorWarp;
    private Tripulacion tripulacion;
    private boolean destruida;
    private RadarLargoAlcance radarLargoAlcance;
    private final List<ObservadorNave> observadores = new ArrayList<>();
    private RadarCortoAlcance radarCortoAlcance;
    private int gemas;

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
        if (!destruida) {
            destruida = true;
            notificarObservadores();
        }
    }
    
    public void verificarDestruccion() {
        if (recursos.getEnergia() == 0 || recursos.getCombustible() == 0 || recursos.getDesgaste() >= 100) {
            destruir();
        }
    }

    public abstract String getTipo();
    
    public String getEstadoActual() {
        return "  Combustible: " + recursos.getCombustible()
                + "\n  Energia: " + recursos.getEnergia()
                + "\n  Desgaste: " + recursos.getDesgaste()
                + "\n  Requiere mantenimiento: " + recursos.requiereMantenimiento()
                + "\n  Motor Warp: " + motorWarp
                + "\n  Gemas: " + this.gemas;
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
        verificarDestruccion();
        notificarObservadores();
    }
    
    public int getEnergia() {
        return recursos.getEnergia();
    }
    
    public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        recursos.cargarEnergia(cantidad);
        notificarObservadores();
    }

    public int getGemas() {
        return gemas;
    }

    public void agregarGemas(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        if (cantidad <= 0) {
            throw new OperacionRecursoInvalidaExcepcion("La cantidad de gemas debe ser positiva");
        }
        gemas += cantidad;
        notificarObservadores();
    }

    // gasto voluntario
    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        recursos.consumirEnergia(cantidad);
        verificarDestruccion();
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
        verificarDestruccion();
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
    public ResultadoInteraccion interactuarCon(Nave nave) {
        ResultadoEncuentro resultado = nave.enfrentar(this);
        if (resultado == ResultadoEncuentro.PIERDE) {
            nave.destruir();
        }
        return new ResultadoInteraccion("Encuentro con " + getNombre()
                + " (" + getTipo() + "). Resultado: "
                + resultado
        );
    }
    
    // Operaciones delegadas sobre el Motor Warp
    public String getEstadoMotorWarp() {
        return motorWarp.toString();
    }

    public ResultadoSalto saltar() throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        // Verificamos antes de avanzar el estado del motor.
        verificarDisponibilidadRecursos(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE, 0, 0);
        ResultadoSalto resultado = motorWarp.saltar();
        aplicarCostosSalto(resultado);
        notificarObservadores();
        return resultado;
    }

    public ResultadoSalto saltarWarp() throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        // Si el Warp no está disponible, se realiza un salto normal
        if (!puedeSaltarWarp()) {
            return saltar();
        }
        // Validamos antes de cambiar el estado a Enfriamiento.
        verificarDisponibilidadRecursos(0, CostosMision.COSTO_ENERGIA_WARP, 0);
        ResultadoSalto resultado = motorWarp.saltarWarp(true);
        aplicarCostosSalto(resultado);
        notificarObservadores();
        return resultado;
    }

    private void aplicarCostosSalto(ResultadoSalto resultado) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        if (resultado.getTipo() == TipoResultadoSalto.SALTO_NORMAL || resultado.getTipo() == TipoResultadoSalto.ENFRIAMIENTO) {
            consumirCombustible(CostosMision.COSTO_MOVIMIENTO_COMBUSTIBLE);
        }

        if (resultado.getTipo() == TipoResultadoSalto.SALTO_WARP) {
            consumirEnergia(CostosMision.COSTO_ENERGIA_WARP);
        }

        if (resultado.getTipo() == TipoResultadoSalto.ENFRIAMIENTO) {
            int desgasteAplicado = Math.min(CostosMision.COSTO_DESGASTE_ENFRIAMIENTO_WARP, 100 - recursos.getDesgaste());
            if (desgasteAplicado > 0) {
                aumentarDesgaste(desgasteAplicado);
            }
        }
        
        verificarDestruccion();
    }
    
    public boolean puedeSaltarWarp() {
        return motorWarp.puedeSaltarWarp() && recursos.getEnergia() >= CostosMision.COSTO_ENERGIA_WARP;
    }
    
    // Metodos usados para las Misiones
    public Ruta usarRadarLargoAlcance(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        consumirEnergia(CostosMision.COSTO_RADAR_LARGO);
        return radarLargoAlcance.calcularRuta(origen, destino);
    }
    
    public ElementoDelUniverso[][] usarRadarCortoAlcance(Tablero tablero) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        consumirEnergia(CostosMision.COSTO_RADAR_CORTO);
        return radarCortoAlcance.escanear(tablero);
    }

    public Ruta recalcularRuta(Posicion origen, Posicion destino) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        consumirEnergia(CostosMision.COSTO_RECALCULO);
        return radarLargoAlcance.calcularRuta(origen, destino);
    }

    // OBSERVADORES
    public void agregarObservador(ObservadorNave observador) {
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

    @Override
    public String toString() {
        return "Nave [nombre = " + nombre + ", tipo = " + getTipo() + "]";
    }

}
