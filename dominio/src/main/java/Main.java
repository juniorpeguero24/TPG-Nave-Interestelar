import controlador.ControladorAcceso;
import controlador.ControladorCreacion;
import controlador.Navegador;
import modelo.excepciones.NaveInvalidaException;
import modelo.nave.Nave;
import modelo.universo.Universo;
import vistas.VControlNave;
import vistas.VCreacion;
import modelo.tablero.Tablero;
import modelo.tablero.Destino;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Asteroide;
import modelo.universo.EstacionCarga;
import modelo.nave.NaveFactory;
import java.util.ArrayList;
import modelo.excepciones.CantidadDeElementosSuperaCasillaExcepcion;
import modelo.excepciones.DimensionesDeTableroInconsistentesExcepcion;

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
            return;
        }
    }

}