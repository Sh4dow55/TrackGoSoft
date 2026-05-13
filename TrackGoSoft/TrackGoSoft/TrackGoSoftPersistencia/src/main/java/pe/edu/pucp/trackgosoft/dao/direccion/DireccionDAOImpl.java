package pe.edu.pucp.trackgosoft.dao.direccion;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.direccion.Direccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DireccionDAOImpl extends DefaultBaseDAO<Direccion> implements DireccionDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Direccion modelo) throws SQLException {
        String sql = "INSERT INTO direccion(departamento, provincia, distrito, codPostal, calleNumero, referencia) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getDepartamento());
        cmd.setString(2, modelo.getProvincia());
        cmd.setString(3, modelo.getDistrito());
        cmd.setString(4, modelo.getCodPostal());
        cmd.setString(5, modelo.getCalleNumero());
        cmd.setString(6, modelo.getReferencia());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Direccion modelo) throws SQLException {
        String sql = "UPDATE direccion SET departamento=?, provincia=?, distrito=?, codPostal=?, calleNumero=?, referencia=? WHERE idDireccion=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getDepartamento());
        cmd.setString(2, modelo.getProvincia());
        cmd.setString(3, modelo.getDistrito());
        cmd.setString(4, modelo.getCodPostal());
        cmd.setString(5, modelo.getCalleNumero());
        cmd.setString(6, modelo.getReferencia());
        cmd.setInt(7, modelo.getIdDireccion());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM direccion WHERE idDireccion=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM direccion WHERE idDireccion=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM direccion";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Direccion mapearModelo(ResultSet rs) throws SQLException {
        Direccion direccion = new Direccion(
                rs.getString("departamento"),
                rs.getString("provincia"),
                rs.getString("distrito"),
                rs.getString("codPostal"),
                rs.getString("calleNumero"),
                rs.getString("referencia")
        );
        direccion.setIdDireccion(rs.getInt("idDireccion"));
        return direccion;
    }
}
