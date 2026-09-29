package paquete.mision;

import paquete.Nave;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class Mision02 extends Mision {
    private boolean elementoObtenido = false;
    
    public Mision02(Nave nave) {
        super(nave, "M-02 - Recoleccion", 4, 5, 4);
    }
    
    @Override
    protected void ejecutar() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        nave.consumirCombustible(getCombustibleNecesario());         
        nave.aumentarDesgaste(getDesgasteGenerado());
        
        // ejecutar acciones
        elementoObtenido = true;
        nave.consumirEnergia(getEnergiaNecesaria());
    }
    
    @Override
    protected void evaluar() {
        if (elementoObtenido) {
            resultado = ResultadoMision.EXITO;
        } else {
            resultado = ResultadoMision.FALLO;
        }
    }
}
