package pe.edu.pucp.trackgosoft.dao.usuario;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.usuario.Administrador;
import pe.edu.pucp.trackgosoft.modelo.usuario.Cargo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdministradorDAOImpl extends DefaultBaseDAO<Administrador> implements AdministradorDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Administrador modelo) throws SQLException {
        String sql = "INSERT INTO administrador(codigoEmpleado, dni, nombres, apellidos, correo, contrasenaHash, telefono, fechaRegistro, estado, cargo, nivelDeAcceso, isManager) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getCodigoEmpleado());
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombres());
        cmd.setString(4, modelo.getApellidos());
        cmd.setString(5, modelo.getCorreo());
        cmd.setString(6, modelo.getContrasenhaHash());
        cmd.setString(7, modelo.getTelefono());
        cmd.setDate(8, new java.sql.Date(modelo.getFechaRegistro().getTime()));
        cmd.setBoolean(9, modelo.getEstado());
        cmd.setString(10, modelo.getCargo().name());
        cmd.setString(11, modelo.getNivelDeAcceso());
        cmd.setBoolean(12, modelo.getIsManager());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Administrador modelo) throws SQLException {
        String sql = "UPDATE administrador SET codigoEmpleado=?, dni=?, nombres=?, apellidos=?, correo=?, contrasenaHash=?, telefono=?, fechaRegistro=?, estado=?, cargo=?, nivelDeAcceso=?, isManager=? WHERE idUsuario=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getCodigoEmpleado());
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombres());
        cmd.setString(4, modelo.getApellidos());
        cmd.setString(5, modelo.getCorreo());
        cmd.setString(6, modelo.getContrasenhaHash());
        cmd.setString(7, modelo.getTelefono());
        cmd.setDate(8, new java.sql.Date(modelo.getFechaRegistro().getTime()));
        cmd.setBoolean(9, modelo.getEstado());
        cmd.setString(10, modelo.getCargo().name());
        cmd.setString(11, modelo.getNivelDeAcceso());
        cmd.setBoolean(12, modelo.getIsManager());
        cmd.setInt(13, modelo.getIdUsuario());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM administrador WHERE idUsuario=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE idUsuario=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM administrador";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Administrador mapearModelo(ResultSet rs) throws SQLException {
        Administrador admin = new Administrador(
                rs.getString("codigoEmpleado"),
                rs.getString("dni"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("correo"),
                rs.getString("contrasenaHash"),
                rs.getString("telefono"),
                rs.getString("nivelDeAcceso")
        );
        admin.setIdUsuario(rs.getInt("idUsuario"));
        admin.setFechaRegistro(rs.getDate("fechaRegistro"));
        admin.setEstado(rs.getBoolean("estado"));
        admin.setCargo(Cargo.valueOf(rs.getString("cargo")));
        admin.setIsManager(rs.getBoolean("isManager"));
        return admin;
    }

    @Override
    public Administrador autenticar(String correo, String contrasenaHash) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM administrador WHERE correo=? AND contrasenaHash=? AND estado=1";
            try (PreparedStatement cmd = conn.prepareStatement(sql)) {
                cmd.setString(1, correo);
                cmd.setString(2, contrasenaHash);
                try (ResultSet rs = cmd.executeQuery()) {
                    if (rs.next()) {
                        return mapearModelo(rs);
                    }
                }
            }
            return null;
        });
    }
}
