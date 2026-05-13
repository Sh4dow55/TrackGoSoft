package pe.edu.pucp.trackgosoft.dao.usuario;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.usuario.Empleado;

public interface EmpleadoDAO extends Persistible<Empleado, Integer> {
    Empleado autenticar(String correo, String contrasenaHash);
}
