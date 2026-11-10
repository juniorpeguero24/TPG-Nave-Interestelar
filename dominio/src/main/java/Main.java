import controlador.ControladorAcceso;
import controlador.Navegador;
import modelo.universo.Universo;


import modelo.persistencia.InicializadorBD;

import vistas.VAcceso;

public class Main {
    public static void main(String[] args) {

        inicializarBD();

        // VISTA

        VAcceso vAcceso = new VAcceso();

        // MODELO

        Universo universo = new Universo();

        // CONTROLADORES

        Navegador navegador = new Navegador(vAcceso);
        new ControladorAcceso(vAcceso, navegador, universo);

        vAcceso.setVisible(true);

    }


    private static void inicializarBD() {
        // inicializar BD
        try {
            InicializadorBD.inicializar();
        } catch (Exception e) {
            System.out.println("Error al inicializar la BD: " + e.getMessage());
        }
    }

}