package modelo.persistencia;

public class PruebaPersistencia {
    public static void main(String[] args) {
        try {
            InicializadorBD.inicializar();

            IUsuarioDAO dao = new UsuarioDAO();

            UsuarioDTO usuario =
                    new UsuarioDTO("seba", "1234");

            if (!dao.existeUsuario(usuario.getNombreUsuario())) {
                dao.registrar(usuario);
                System.out.println("Usuario registrado");
            } else {
                System.out.println("El usuario ya existia");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
