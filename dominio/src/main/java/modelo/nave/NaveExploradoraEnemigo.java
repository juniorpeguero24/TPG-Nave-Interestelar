package modelo.nave;

public class NaveEnemigaExploradora implements NaveExterna {

    @Override
    public ResultadoEncuentro interactuarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.PACTO;
    }

    @Override
    public ResultadoEncuentro interactuarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.PIERDE;
    }

    @Override
    public ResultadoEncuentro interactuarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.GANA;
    }
}