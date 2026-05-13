package pe.edu.pucp.trackgosoft.modelo.usuario;

public class Administrador extends Usuario{
    private String nivelDeAcceso;
    private boolean isManager;

    public Administrador(){
        super();
    };

    public Administrador(String codigoEmpleado, String dni, String nombres, String apellidos, String correo,
                         String contrasenhaHash, String telefono, String nivelDeAcceso){
        super(codigoEmpleado,dni,nombres,apellidos,correo,contrasenhaHash,telefono,Cargo.ADMINISTRADOR);
        this.nivelDeAcceso = nivelDeAcceso;
        this.isManager = false;
    }

    public String getNivelDeAcceso(){
        return nivelDeAcceso;
    }

    public void setNivelDeAcceso(String nivelDeAcceso){
        this.nivelDeAcceso = nivelDeAcceso;
    }

    public boolean getIsManager(){
        return isManager;
    }

    public void setIsManager(boolean isManager){
        this.isManager = isManager;
    }
}
