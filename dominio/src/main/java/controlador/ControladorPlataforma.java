package controlador;

import modelo.concurrencia.*;
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
            this.VVisor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            this.navegador = nav;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            if (comando.equalsIgnoreCase("VOLVER")) {
                volverAlControl();
            } else {
                String nombreNave = this.VVisor.getNombreNave();
                int tiempo = this.VVisor.getTiempo();

                if (comando.equalsIgnoreCase("NaveConsu")) {
                    iniciarNave(new NaveConsumidora(nombreNave, this.plataforma, tiempo));
                } else
                    if (comando.equalsIgnoreCase("NaveGen")) {
                        iniciarNave(new GeneradoraDeEnergia(nombreNave, this.plataforma, tiempo));
                    }
                    else
                        if  (comando.equalsIgnoreCase("detener")) {
                            detenerNaves();
                        }
            }
        }

        private void iniciarNave(Runnable nave) {
            Thread hilo = new Thread(nave);
            hilo.setDaemon(true);
            this.hilosNaves.add(hilo);
            hilo.start();
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
