package paquete.mision;

import paquete.Nave;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    private String nombre;
    
    public Mision01(Nave nave) {
        super(nave, "M-01 - Intercepcion y asistencia", 4, 5, 4);
    }
    
    @Override
    protected void ejecutar() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        nave.consumirCombustible(getCombustibleNecesario()); 
        nave.aumentarDesgaste(getDesgasteGenerado());
        
        // ejecutar acciones
        asistenciaRealizada = true;
        nave.consumirEnergia(getEnergiaNecesaria());
    }
    
    @Override
    protected void evaluar() {
        if (asistenciaRealizada) {
            resultado = ResultadoMision.EXITO;
        } else {
            resultado = ResultadoMision.FALLO;
        }
    }
}
