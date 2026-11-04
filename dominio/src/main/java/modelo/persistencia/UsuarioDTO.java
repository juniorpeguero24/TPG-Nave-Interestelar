package modelo.persistencia;

public class UsuarioDTO {
    private String nombreUsuario;
    private String contrasenia;

    public UsuarioDTO(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}