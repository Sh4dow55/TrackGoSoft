package pe.edu.pucp.trackgosoft.modelo.usuario;

import java.util.Date;
import pe.edu.pucp.trackgosoft.modelo.interfaces.Consultable;

public abstract class Usuario implements Consultable {
    private int idUsuario;
    private String codigoEmpleado;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasenhaHash;
    private String telefono;
    private Date fechaRegistro;
    private boolean estado;
    private Cargo cargo;

    public Usuario() {}

    public Usuario(String codigoEmpleado, String dni, String nombres, String apellidos,
                   String correo, String contrasenhaHash, String telefono, Cargo cargo) {
        this.idUsuario = -1;
        this.codigoEmpleado = codigoEmpleado;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenhaHash = contrasenhaHash;
        this.telefono = telefono;
        this.fechaRegistro = new Date();
        this.estado = true;
        this.cargo = cargo;
    }

    public Usuario(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.codigoEmpleado = usuario.getCodigoEmpleado();
        this.dni = usuario.getDni();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.correo = usuario.getCorreo();
        this.contrasenhaHash = usuario.getContrasenhaHash();
        this.telefono = usuario.getTelefono();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.estado = usuario.getEstado();
        this.cargo = usuario.getCargo();
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenhaHash() { return contrasenhaHash; }
    public void setContrasenhaHash(String contrasenhaHash) { this.contrasenhaHash = contrasenhaHash; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    @Override
    public int consultarEstadoActual(){
        if(estado){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "ID: " + idUsuario + " | " + nombres + " " + apellidos + " | Cargo: " + cargo;
    }
}
