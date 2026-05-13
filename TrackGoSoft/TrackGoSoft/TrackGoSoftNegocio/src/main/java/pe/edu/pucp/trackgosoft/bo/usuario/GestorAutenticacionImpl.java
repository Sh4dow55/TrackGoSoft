package pe.edu.pucp.trackgosoft.bo.usuario;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.dao.usuario.AdministradorDAO;
import pe.edu.pucp.trackgosoft.dao.usuario.AdministradorDAOImpl;
import pe.edu.pucp.trackgosoft.dao.usuario.EmpleadoDAO;
import pe.edu.pucp.trackgosoft.dao.usuario.EmpleadoDAOImpl;
import pe.edu.pucp.trackgosoft.db.utils.Crypto;
import pe.edu.pucp.trackgosoft.modelo.usuario.Usuario;

public class GestorAutenticacionImpl extends BaseBO implements GestorAutenticacion {

    private final AdministradorDAO administradorDAO;
    private final EmpleadoDAO empleadoDAO;

    public GestorAutenticacionImpl() {
        this.administradorDAO = new AdministradorDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    @Override
    public Usuario login(String correo, String contrasenaPlana) {
        validarTextoObligatorio(correo, "Correo");
        validarTextoObligatorio(contrasenaPlana, "Contraseña");

        // 1. Encriptar la contraseña introducida por el usuario
        String contrasenaHash;
        try {
            contrasenaHash = Crypto.encrypt(contrasenaPlana);
        } catch (Exception e) {
            throw new RuntimeException("Error interno al procesar las credenciales de seguridad.", e);
        }

        // 2. Buscar primero en la tabla de Administradores
        Usuario usuario = administradorDAO.autenticar(correo, contrasenaHash);
        
        // 3. Si no es administrador, buscar en la tabla de Empleados
        if (usuario == null) {
            usuario = empleadoDAO.autenticar(correo, contrasenaHash);
        }

        // 4. Si después de buscar en ambas tablas sigue siendo nulo, las credenciales son inválidas
        if (usuario == null) {
            throw new RuntimeException("Credenciales incorrectas o usuario inactivo.");
        }

        return usuario;
    }
}
