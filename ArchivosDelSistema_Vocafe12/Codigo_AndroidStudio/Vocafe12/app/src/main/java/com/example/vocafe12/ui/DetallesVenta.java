package com.example.vocafe12.ui;

public class DetallesVenta {
    private String nombreProductoV,cantidadV,importeV,detallesV,fotoV;

    public DetallesVenta() {
    }

    public DetallesVenta(String nombreProductoV, String cantidadV, String importeV, String detallesV, String fotoV) {
        this.nombreProductoV = nombreProductoV;
        this.cantidadV = cantidadV;
        this.importeV = importeV;
        this.detallesV = detallesV;
        this.fotoV = fotoV;
    }

    public String getNombreProductoV() {
        return nombreProductoV;
    }

    public void setNombreProductoV(String nombreProductoV) {
        this.nombreProductoV = nombreProductoV;
    }

    public String getCantidadV() {
        return cantidadV;
    }

    public void setCantidadV(String cantidadV) {
        this.cantidadV = cantidadV;
    }

    public String getImporteV() {
        return importeV;
    }

    public void setImporteV(String importeV) {
        this.importeV = importeV;
    }

    public String getDetallesV() {
        return detallesV;
    }

    public void setDetallesV(String detallesV) {
        this.detallesV = detallesV;
    }

    public String getFotoV() {
        return fotoV;
    }

    public void setFotoV(String fotoV) {
        this.fotoV = fotoV;
    }
}
