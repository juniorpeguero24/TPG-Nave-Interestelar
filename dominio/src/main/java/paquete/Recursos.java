package paquete;

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
    
    public void cargarCombustible(int cantidad){
        if (cantidad <= 0 || combustible + cantidad > CAPACIDAD_MAX_COMBUSTIBLE) {
            throw new IllegalArgumentException("Cantidad de combustible invalida");
        }
        combustible += cantidad;
    }
    
    public void cargarEnergia(int cantidad){
        if (cantidad <= 0 || energia + cantidad > CAPACIDAD_MAX_ENERGIA) {
            throw new IllegalArgumentException("Cantidad de energia invalida");
        }
        energia += cantidad;
    }
    
    public void realizarMantenimiento(){
        this.desgaste = 0;
    }
    
    public boolean requiereMantenimiento(){
        return this.desgaste >= LIMITE_DESGASTE_MANTENIMIENTO;
    }    
    
    public void consumirCombustible(int cantidad) {
        if (cantidad <= 0 || cantidad > combustible) {
            throw new IllegalArgumentException("Combustible insuficiente");
        }
        combustible -= cantidad;
    }
    
    public void consumirEnergia(int cantidad) {
        if (cantidad <= 0 || cantidad > energia) {
            throw new IllegalArgumentException("Energia insuficiente");
        }
        energia -= cantidad;
    }
    
    public void aumentarDesgaste(int cantidad) {
        if (cantidad <= 0 || cantidad + desgaste > DESGASTE_MAXIMO) {
            throw new IllegalArgumentException("Desgaste invalido");
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
}
