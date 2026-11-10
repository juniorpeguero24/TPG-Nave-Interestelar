package modelo.mision;

import java.util.ArrayList;

public class InformeMision {
    private final String misionEjecutada;
    private final ResultadoMision resultado;
    private final ArrayList<String> accionesPrincipales;

    private final int combustibleConsumido;
    private final int energiaConsumida;
    private final int desgasteGenerado;

    private final String estadoFinalNave;
    private final ArrayList<String> observaciones;

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
        this.accionesPrincipales =  new ArrayList<>(accionesPrincipales);
        this.combustibleConsumido = combustibleConsumido;
        this.energiaConsumida = energiaConsumida;
        this.desgasteGenerado = desgasteGenerado;
        this.estadoFinalNave = estadoFinalNave;
        this.observaciones =  new ArrayList<>(observaciones);
    }

    public String getMisionEjecutada() {
        return misionEjecutada;
    }
    
    public ResultadoMision getResultado() {
        return resultado;
    }
    
    public ArrayList<String> getAccionesPrincipales() {
        return new ArrayList<>(accionesPrincipales);
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
        return new ArrayList<>(observaciones);
    }
    
    protected void agregarAccionPrincipal(String accion) {
        accionesPrincipales.add(accion);
    }

    protected void agregarObservacion(String observacion) {
        observaciones.add(observacion);
    }
    
    @Override
    public String toString() {
        String texto = "";

        texto += "INFORME DE MISION\n";
        texto += "Mision ejecutada: " + misionEjecutada + "\n";
        texto += "Resultado: " + resultado + "\n\n";

        texto += "ACCIONES PRINCIPALES:\n";
        if (accionesPrincipales.isEmpty()) {
            texto += "  Ninguna accion registrada\n";
        } else {
            for (String accion : accionesPrincipales) {
                texto += "  - " + accion + "\n";
            }
        }

        texto += "\nRECURSOS CONSUMIDOS:\n";
        texto += "  Combustible: " + combustibleConsumido + "\n";
        texto += "  Energia: " + energiaConsumida + "\n";
        texto += "  Desgaste generado: " + desgasteGenerado + "\n";

        texto += "\nESTADO FINAL DE LA NAVE:\n";
        texto += estadoFinalNave + "\n";

        texto += "\nOBSERVACIONES:\n";
        if (observaciones.isEmpty()) {
            texto += "  Ninguna registrada\n";
        } else {
            for (String observacion : observaciones) {
                texto += "  - " + observacion + "\n";
            }
        }

        return texto;
    }
}