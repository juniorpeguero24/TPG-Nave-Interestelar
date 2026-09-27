package paquete;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class Recursos {
    private int combustible, energia, desgaste;

    private static final int CAPACIDAD_MAX_COMBUSTIBLE = 100;
    private static final int CAPACIDAD_MAX_ENERGIA = 100;
    private static final int DESGASTE_MAXIMO = 100;
    private static final int LIMITE_DESGASTE_MANTENIMIENTO = 80;
    
    
    public Recursos(int combustible, int energia) {
        // Validacion de atributos iniciales
        if (combustible < 0 || combustible > CAPACIDAD_MAX_COMBUSTIBLE) {
            throw new IllegalArgumentException("Combustible inicial invalido");
        }

        if (energia < 0 || energia > CAPACIDAD_MAX_ENERGIA) {
            throw new IllegalArgumentException("Energia inicial invalida");
        }
            
        this.combustible = combustible;
        this.energia = energia;
        this.desgaste = 0;
    }
    
    public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        if (cantidad <= 0) {
            throw new OperacionRecursoInvalidaExcepcion("La cantidad de combustible a cargar debe ser positiva");
        }
        if (combustible + cantidad > CAPACIDAD_MAX_COMBUSTIBLE) {
            throw new OperacionRecursoInvalidaExcepcion("La carga supera la capacidad maxima de combustible");
        }
        combustible += cantidad;
    }
    
    public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        if (cantidad <= 0) {
            throw new OperacionRecursoInvalidaExcepcion("La cantidad de energia a cargar debe ser positiva");
        }
        if (energia + cantidad > CAPACIDAD_MAX_ENERGIA) {
            throw new OperacionRecursoInvalidaExcepcion("La carga supera la capacidad maxima de energia");
        }
        energia += cantidad;
    }
    
    public void realizarMantenimiento(){
        this.desgaste = 0;
    }
    
    public boolean requiereMantenimiento(){
        return this.desgaste >= LIMITE_DESGASTE_MANTENIMIENTO;
    }    
    
    public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        if (cantidad < 0) {
            throw new OperacionRecursoInvalidaExcepcion("El combustible a consumir debe ser positivo");
        }
        if (cantidad > combustible) {
            throw new RecursoInsuficienteExcepcion("Combustible insuficiente");
        }
        combustible -= cantidad;
    }
    
    public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion {
        if (cantidad < 0) {
            throw new OperacionRecursoInvalidaExcepcion("La energia a consumir debe ser positiva");
        }
        if (cantidad > energia) {
            throw new RecursoInsuficienteExcepcion("Energia insuficiente");
        }
        energia -= cantidad;
    }
    
    public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion {
        if (cantidad < 0) {
            throw new OperacionRecursoInvalidaExcepcion("El desgaste debe ser positivo");
        }
        if (cantidad + desgaste > DESGASTE_MAXIMO) {
            throw new OperacionRecursoInvalidaExcepcion("El desgaste supera el limite maximo");
        }
        desgaste += cantidad;
    }
    
    public int getCombustible() {
        return combustible;
    }

    public int getEnergia() {
        return energia;
    }

    public int getDesgaste() {
        return desgaste;
    }
    
    public void verificarDisponibilidadRecursos(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (combustibleNecesario < 0 || energiaNecesaria < 0 || desgasteGenerado < 0) {
            throw new OperacionRecursoInvalidaExcepcion("Los recursos necesarios no pueden ser negativos");
        }
        if (combustibleNecesario > combustible) {
            throw new RecursoInsuficienteExcepcion("Combustible insuficiente");
        }
        if (energiaNecesaria > energia) {
            throw new RecursoInsuficienteExcepcion("Energia insuficiente");
        }
        if (desgaste + desgasteGenerado > DESGASTE_MAXIMO) {
            throw new OperacionRecursoInvalidaExcepcion("El desgaste supera el limite maximo");
        }
    }
}
