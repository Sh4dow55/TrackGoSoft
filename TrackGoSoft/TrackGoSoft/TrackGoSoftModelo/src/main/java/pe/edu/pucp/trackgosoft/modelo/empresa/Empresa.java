package pe.edu.pucp.trackgosoft.modelo.empresa;

import java.util.Date;

public class Empresa {
    private int idEmpresa;
    private String nombre;
    private String ruc;
    private String direccion;
    private String sector;
    private Date fechaFundacion;

    public Empresa() {}

    public Empresa(String nombre, String ruc, String direccion, String sector,
                   Date fechaFundacion){
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.sector = sector;
        this.fechaFundacion = fechaFundacion;
    }

    public Empresa(Empresa empresa){
        this.idEmpresa=empresa.idEmpresa;
        this.nombre = empresa.nombre;
        this.ruc = empresa.ruc;
        this.direccion = empresa.direccion;
        this.sector = empresa.sector;
        this.fechaFundacion = empresa.fechaFundacion;
    }

    public void setId(int id){
        idEmpresa = id;
    }

    public int getId(){
        return idEmpresa;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getRuc(){
        return ruc;
    }

    public void setRuc(String ruc){
        this.ruc = ruc;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public String getSector(){
        return sector;
    }

    public void setSector(String sector){
        this.sector = sector;
    }

    public Date getFechaFundacion(){
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion){
        this.fechaFundacion = fechaFundacion;
    }

    @Override
    public String toString() {
        return "--- EMPRESA ---" + "\nID: " + idEmpresa + "\nNombre: " + nombre + "\nRUC: " + ruc + "\nDirección: " + direccion +
                "\nSector: " + sector + "\nFecha Fundación: " + fechaFundacion + "\n------";
    }
}
