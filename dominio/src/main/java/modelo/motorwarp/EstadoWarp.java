package modelo.motorwarp;

public interface EstadoWarp {
    ResultadoSalto saltar();
    ResultadoSalto saltarWarp(boolean energiaSuficiente);
}
