package controlador;

import modelo.persistencia.IUsuarioDAO;
import modelo.persistencia.UsuarioDAO;
import modelo.persistencia.UsuarioDTO;
import modelo.universo.Universo;
import vistas.VAcceso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

public class ControladorAcceso implements ActionListener {
    private final VAcceso vista;
    private final IUsuarioDAO usuarioDAO;
    private final Navegador navegador;
    private final Universo universo;

    public ControladorAcceso(VAcceso vista, Navegador navegador, Universo universo) {
        this.vista = vista;
        this.navegador = navegador;
        this.universo = universo;
        this.usuarioDAO = new UsuarioDAO();
        this.vista.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equalsIgnoreCase("Registrarse")) {
            registrar();
        }
        else if (comando.equalsIgnoreCase("Entrar")) {
            login();
        }
    }

    private void registrar() {
        String nombre = vista.getNombre();
        String contrasenia = vista.getContrasenia();
        if (nombre.isBlank() || contrasenia.isBlank()) {
            vista.mostrarMensaje("Debe completar todos los campos");
            return;
        }
        try {
            if (usuarioDAO.existeUsuario(nombre)) {
                vista.mostrarMensaje("El usuario ya existe");
                return;
            }
            UsuarioDTO usuario = new UsuarioDTO(nombre, contrasenia);
            usuarioDAO.registrar(usuario);
            vista.mostrarMensaje("Registro exitoso");
            vista.limpiarCampos();
        } catch (SQLException  e) {
            vista.mostrarMensaje("Error al registrar usuario: " + e.getMessage());
        }
    }

    private void login() {
        String nombre = vista.getNombre();
        String contrasenia = vista.getContrasenia();
        if (nombre.isBlank() || contrasenia.isBlank()) {
            vista.mostrarMensaje("Debe completar todos los campos");
            return;
        }
        try {
            if (usuarioDAO.validarCredenciales(nombre, contrasenia)) {
                navegador.mostrarCreacion(universo);
            } else {
                vista.mostrarMensaje("Usuario o contraseña incorrectos");
            }
        } catch (SQLException  e) {
            vista.mostrarMensaje("Error al iniciar sesión: " + e.getMessage());
        }
    }
}