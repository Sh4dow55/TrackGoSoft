package pe.edu.pucp.trackgosoft.dao.usuario;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.usuario.Administrador;

public interface AdministradorDAO extends Persistible<Administrador, Integer> {
    Administrador autenticar(String correo, String contrasenaHash);
}
