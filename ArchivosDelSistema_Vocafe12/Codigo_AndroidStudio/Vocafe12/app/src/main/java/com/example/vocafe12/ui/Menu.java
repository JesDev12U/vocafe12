package com.example.vocafe12.ui;

public class Menu {
    private String nombreM,descripcionM,precioM,fotoM;

    public Menu() {
    }

    public Menu(String nombreM, String descripcionM, String precioM, String fotoM) {
        this.nombreM = nombreM;
        this.descripcionM = descripcionM;
        this.precioM = precioM;
        this.fotoM = fotoM;
    }

    public String getNombreM() {
        return nombreM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public String getDescripcionM() {
        return descripcionM;
    }

    public void setDescripcionM(String descripcionM) {
        this.descripcionM = descripcionM;
    }

    public String getPrecioM() {
        return precioM;
    }

    public void setPrecioM(String precioM) {
        this.precioM = precioM;
    }

    public String getFotoM() {
        return fotoM;
    }

    public void setFotoM(String fotoM) {
        this.fotoM = fotoM;
    }
}
