package modelo.nave;

public interface NaveExterna {
    ResultadoEncuentro interactuarConExploradora(NaveExploradora exploradora);
    ResultadoEncuentro interactuarConCarguero(NaveCarguero carguero);
    ResultadoEncuentro interactuarConCombate(NaveCombate combate);
}