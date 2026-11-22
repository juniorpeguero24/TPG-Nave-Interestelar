import controlador.ControladorAcceso;
import controlador.Navegador;
import modelo.universo.Universo;

import vistas.VAcceso;

public class Main {
    public static void main(String[] args) {

        // VISTA
        VAcceso vAcceso = new VAcceso();

        // MODELO
        Universo universo = new Universo();

        // CONTROLADORES
        Navegador navegador = new Navegador(vAcceso);
        new ControladorAcceso(vAcceso, navegador, universo);

        vAcceso.setVisible(true);

    }

}