package com.example.vocafe12.ui;

import java.io.Serializable;

public class ComidaPedido implements Serializable {
    private String nombreP,precioP,fotoP;

    public ComidaPedido() {
    }

    public ComidaPedido(String nombreP, String precioP, String fotoP) {
        this.nombreP = nombreP;
        this.precioP = precioP;
        this.fotoP = fotoP;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getPrecioP() {
        return precioP;
    }

    public void setPrecioP(String precioP) {
        this.precioP = precioP;
    }

    public String getFotoP() {
        return fotoP;
    }

    public void setFotoP(String fotoP) {
        this.fotoP = fotoP;
    }
}
