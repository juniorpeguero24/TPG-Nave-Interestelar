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
    public static void main(String[] args) throws InterruptedException, NaveInvalidaException {
        // inicializar BD
        try {
            InicializadorBD.inicializar();
        } catch (Exception e) {
            System.out.println("Error al inicializar la BD: " + e.getMessage());
            return;
        }
                
        // Creacion del tablero
        int cantFilas = 10;
        int cantColumnas = 10;
        ArrayList<ElementoDelUniverso> elementos = new ArrayList<>();

        elementos.add(new Asteroide(5));
        elementos.add(new Asteroide(7));
        elementos.add(new Asteroide(2));
        elementos.add(new Asteroide(5));
        elementos.add(new Asteroide(7));
        elementos.add(new Asteroide(2));
        elementos.add(new Asteroide(5));
        elementos.add(new Asteroide(7));
        elementos.add(new Asteroide(2));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        try {
            elementos.add(NaveFactory.crearNave("Enemiga 1", "exploradora"));
            elementos.add(NaveFactory.crearNave("Enemiga 2", "combate"));
            elementos.add(NaveFactory.crearNave("Enemiga 3", "carguero"));
        }
        catch (NaveInvalidaException e){
        }

        Tablero tablero;
        try {
            tablero = new Tablero (cantFilas, cantColumnas, elementos);
        }
        catch(DimensionesDeTableroInconsistentesExcepcion | CantidadDeElementosSuperaCasillaExcepcion e) {
            System.out.println("Error al crear el tablero: " + e.getMessage());
            return;
        }
        Destino destino = new Destino(tablero);

        // VISTAS

        VAcceso vAcceso = new VAcceso();

        // MODELOS

        Universo universo = new Universo();

        // CONTROLADORES

        Navegador navegador = new Navegador(vAcceso, destino, tablero);
        new ControladorAcceso(vAcceso, navegador, universo);


        // OBSERVADORES

        vAcceso.setVisible(true);


    }
}
