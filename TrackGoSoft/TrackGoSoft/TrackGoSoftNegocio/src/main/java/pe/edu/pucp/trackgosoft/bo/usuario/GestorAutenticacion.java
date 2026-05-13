package pe.edu.pucp.trackgosoft.bo.usuario;

import pe.edu.pucp.trackgosoft.modelo.usuario.Usuario;

/**
 * Interfaz para la gestión centralizada de autenticación.
 */
public interface GestorAutenticacion {
    /**
     * Valida las credenciales de un usuario.
     * 
     * @param correo El correo electrónico registrado
     * @param contrasenaPlana La contraseña en texto plano (sin encriptar)
     * @return El objeto Usuario (Administrador o Empleado) si es exitoso
     * @throws RuntimeException Si las credenciales son incorrectas
     */
    Usuario login(String correo, String contrasenaPlana);
}
