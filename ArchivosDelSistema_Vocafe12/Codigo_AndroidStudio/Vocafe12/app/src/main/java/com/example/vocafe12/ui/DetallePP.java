package com.example.vocafe12.ui;

public class DetallePP {
    private String nombreProducto,cantidad,importe,detalles,foto;

    public DetallePP() {
    }

    public DetallePP(String nombreProducto, String cantidad, String importe, String detalles, String foto) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.importe = importe;
        this.detalles = detalles;
        this.foto = foto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    public String getFoto(){
        return foto;
    }
    public void setFoto(String foto){
        this.foto=foto;
    }
}
