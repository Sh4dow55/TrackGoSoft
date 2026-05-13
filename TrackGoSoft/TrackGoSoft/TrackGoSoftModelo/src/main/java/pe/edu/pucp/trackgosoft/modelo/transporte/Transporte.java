package pe.edu.pucp.trackgosoft.modelo.transporte;

public class Transporte {
    private String placa;
    private String tipo;
    private String marca;
    private String modelo;

    public Transporte(){};

    public Transporte(String placa, String tipo, String marca, String modelo){
        this.placa = placa;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getMarca(){
        return marca;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "-- TRANSPORTE --" +
                "\nPlaca: " + placa +
                "\nTipo: " + tipo +
                "\nMarca: " + marca +
                "\nModelo: " + modelo +
                "\n---------";
    }
}
