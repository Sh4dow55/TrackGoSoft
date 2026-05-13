package pe.edu.pucp.trackgosoft.dao.usuario;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.usuario.Cargo;
import pe.edu.pucp.trackgosoft.modelo.usuario.Empleado;
import pe.edu.pucp.trackgosoft.modelo.usuario.Turno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadoDAOImpl extends DefaultBaseDAO<Empleado> implements EmpleadoDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Empleado modelo) throws SQLException {
        String sql = "INSERT INTO empleado(codigoEmpleado, dni, nombres, apellidos, correo, contrasenaHash, telefono, fechaRegistro, estado, cargo, licencia, turno) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        cmd.setString(11, modelo.getLicencia());
        cmd.setString(12, modelo.getTurno() != null ? modelo.getTurno().name() : null);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Empleado modelo) throws SQLException {
        String sql = "UPDATE empleado SET codigoEmpleado=?, dni=?, nombres=?, apellidos=?, correo=?, contrasenaHash=?, telefono=?, fechaRegistro=?, estado=?, cargo=?, licencia=?, turno=? WHERE idUsuario=?";
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
        cmd.setString(11, modelo.getLicencia());
        cmd.setString(12, modelo.getTurno() != null ? modelo.getTurno().name() : null);
        cmd.setInt(13, modelo.getIdUsuario());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM empleado WHERE idUsuario=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE idUsuario=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM empleado";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Turno turno = null;
        if (rs.getString("turno") != null) {
            turno = Turno.valueOf(rs.getString("turno"));
        }
        
        Empleado empleado = new Empleado(
                rs.getString("codigoEmpleado"),
                rs.getString("dni"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("correo"),
                rs.getString("contrasenaHash"),
                rs.getString("telefono"),
                Cargo.valueOf(rs.getString("cargo")),
                rs.getString("licencia"),
                turno
        );
        empleado.setIdUsuario(rs.getInt("idUsuario"));
        empleado.setFechaRegistro(rs.getDate("fechaRegistro"));
        empleado.setEstado(rs.getBoolean("estado"));
        return empleado;
    }

    @Override
    public Empleado autenticar(String correo, String contrasenaHash) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM empleado WHERE correo = ? AND contrasenaHash = ? AND estado = 1";
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
