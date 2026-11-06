package controlador;

import modelo.concurrencia.*;
import modelo.excepciones.NaveInvalidaException;
import vistas.VVisorRecondito;
import vistas.VControlNave;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class ControladorPlataforma extends WindowAdapter implements ActionListener
    {
        private final PlataformaDeCarga plataforma;
        private final VVisorRecondito VVisor;
        private final Navegador navegador;
        private final List<Thread> hilosNaves = new ArrayList<>();

        public ControladorPlataforma(PlataformaDeCarga plataforma, VVisorRecondito VVisor,
                                     Navegador nav) {
            this.plataforma = plataforma;
            this.VVisor = VVisor;
            this.VVisor.setActionListener(this);
            this.VVisor.addWindowListener(this);

            this.navegador = nav;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            if (comando.equalsIgnoreCase("VOLVER")) {
                volverAlControl();
            } else {
                if (comando.equalsIgnoreCase("NaveConsu")) {
                    iniciarNaveConsu();
                } else
                    if (comando.equalsIgnoreCase("NaveGen")) {
                        iniciarNaveGen();
                    }
                    else
                        if  (comando.equalsIgnoreCase("detener")) {
                            detenerNaves();
                        }
            }
        }

        private void iniciarNaveConsu() {
           try {
               String nombreNave = this.VVisor.getNombreNave();
               int tiempo = this.VVisor.getTiempo();

               NaveConsumidora consumidora = new NaveConsumidora(nombreNave, this.plataforma, tiempo);

               Thread hilo = new Thread(consumidora);
               hilo.setDaemon(true);
               this.hilosNaves.add(hilo);
               hilo.start();
               this.plataforma.notificarObservadores("¡Ingresó una nueva Nave al universo paralelo!");
           }
           catch (NumberFormatException e){
               this.VVisor.mostrarMensaje("No se pudo crear la nave");
           }
           catch (NaveInvalidaException e){
               this.VVisor.mostrarMensaje(e.getMessage());
           }
        }

        private void iniciarNaveGen() {
            try {
                String nombreNave = this.VVisor.getNombreNave();
                int tiempo = this.VVisor.getTiempo();

                GeneradoraDeEnergia generadora = new GeneradoraDeEnergia(nombreNave, this.plataforma, tiempo);

                Thread hilo = new Thread(generadora);
                hilo.setDaemon(true);
                this.hilosNaves.add(hilo);
                hilo.start();
                this.plataforma.notificarObservadores("¡Ingresó una nueva Generadora al universo paralelo!");
            }
            catch (NumberFormatException e){
                this.VVisor.mostrarMensaje("No se pudo crear la nave");
            }
            catch (NaveInvalidaException e){
                this.VVisor.mostrarMensaje(e.getMessage());
            }
        }

        private void detenerNaves() {
            for (Thread nave : this.hilosNaves) {
                nave.interrupt();
            }
        }

        private void volverAlControl() {
            detenerNaves();
            this.hilosNaves.clear();
            navegador.volverAlControlDesdeVisorRecondito();
        }

        @Override
        public void windowClosing(WindowEvent e) {
            volverAlControl();
        }

    }
