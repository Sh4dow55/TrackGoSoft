package pe.edu.pucp.trackgosoft.modelo.direccion;

public class Direccion {
    private int idDireccion;
    private String departamento;
    private String provincia;
    private String distrito;
    private String codPostal;
    private String calleNumero;
    private String referencia;

    public Direccion() {}

    public Direccion(String departamento, String provincia, String distrito, String codPostal,
                     String calleNumero, String referencia){
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.codPostal = codPostal;
        this.calleNumero = calleNumero;
        this.referencia = referencia;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDepartamento(){
        return departamento;
    }

    public void setDepartamento(String departamento){
        this.departamento = departamento;
    }

    public String getProvincia(){
        return provincia;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }

    public String getDistrito(){
        return distrito;
    }

    public void setDistrito(String distrito){
        this.distrito = distrito;
    }

    public String getCodPostal(){
        return codPostal;
    }

    public void setCodPostal(String codPostal){
        this.codPostal = codPostal;
    }

    public String getCalleNumero(){
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero){
        this.calleNumero = calleNumero;
    }

    public String getReferencia(){
        return referencia;
    }

    public void setReferencia(String referencia){
        this.referencia = referencia;
    }

    @Override
    public String toString() {

        String dep;
        if (departamento != null) dep = departamento;
        else dep = "SIN DEP";

        String prov;
        if (provincia != null) prov = provincia;
        else prov = "SIN PROV";

        String dist;
        if (distrito != null) dist = distrito;
        else dist = "SIN DIST";

        String calle;
        if (calleNumero != null) calle = calleNumero;
        else calle = "SIN CALLE";

        String ref;
        if (referencia != null) ref = referencia;
        else ref = "SIN REFERENCIA";

        return "=== DIRECCION ===\n" +
                dep + " - " + prov + " - " + dist + "\n" +
                calle + "\n" +
                ref +
                "\n=================";
    }
}
