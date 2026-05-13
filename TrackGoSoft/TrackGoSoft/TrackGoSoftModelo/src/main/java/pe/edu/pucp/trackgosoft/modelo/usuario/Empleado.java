package pe.edu.pucp.trackgosoft.modelo.usuario;

public class Empleado extends Usuario {
    private String licencia;
    private Turno turno;

    public Empleado(){
        super();
    }

    public Empleado(String codigoEmpleado, String dni, String nombres, String apellidos, String correo, String contrasenhaHash, String telefono, Cargo cargo, String licencia, Turno turno) {
        super(codigoEmpleado, dni, nombres, apellidos, correo, contrasenhaHash, telefono, cargo);
        this.licencia = licencia;
        this.turno = turno;
    }

    public void setLicencia(String licencia){
        this.licencia = licencia;
    }

    public void setTurno(Turno turno){
        this.turno = turno;
    }

    public String getLicencia(){
        return this.licencia;
    }

    public Turno getTurno(){
        return this.turno;
    }
}
