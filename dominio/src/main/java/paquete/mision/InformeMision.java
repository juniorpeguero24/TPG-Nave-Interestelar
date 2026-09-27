package paquete.mision;

import java.util.ArrayList;

public class InformeMision {
    private String misionEjecutada;
    private ResultadoMision resultado;
    private ArrayList<String> accionesPrincipales;

    private int combustibleConsumido;
    private int energiaConsumida;
    private int desgasteGenerado;

    private String estadoFinalNave;
    private ArrayList<String> observaciones;

    public InformeMision(
            String misionEjecutada,
            ResultadoMision resultado,
            ArrayList<String> accionesPrincipales,
            int combustibleConsumido,
            int energiaConsumida,
            int desgasteGenerado,
            String estadoFinalNave,
            ArrayList<String> observaciones) 
    {
        this.misionEjecutada = misionEjecutada;
        this.resultado = resultado;
        this.accionesPrincipales = accionesPrincipales;
        this.combustibleConsumido = combustibleConsumido;
        this.energiaConsumida = energiaConsumida;
        this.desgasteGenerado = desgasteGenerado;
        this.estadoFinalNave = estadoFinalNave;
        this.observaciones = observaciones;
    }

    public String getMisionEjecutada() {
        return misionEjecutada;
    }
    
    public ResultadoMision getResultado() {
        return resultado;
    }
    
    public ArrayList<String> getAccionesPrincipales() {
        return accionesPrincipales;
    }
    
    public int getCombustibleConsumido() {
        return combustibleConsumido;
    }
    
    public int getEnergiaConsumida() {
        return energiaConsumida;
    }
    
    public int getDesgasteGenerado() {
        return desgasteGenerado;
    }
    
    public String getEstadoFinalNave() {
        return estadoFinalNave;
    }
    
    public ArrayList<String> getObservaciones() {
        return observaciones;
    }
    
    protected void agregarAccionPrincipal(String accion) {
        accionesPrincipales.add(accion);
    }

    protected void agregarObservacion(String observacion) {
        observaciones.add(observacion);
    }
}