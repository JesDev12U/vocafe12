package com.example.vocafe12.ui;

import java.io.Serializable;

public class PDeshabilitados implements Serializable {
    private String nombrePDeshabilitado,fotoPDeshabilitado;

    public PDeshabilitados() {
    }

    public PDeshabilitados(String nombrePDeshabilitado, String fotoPDeshabilitado) {
        this.nombrePDeshabilitado = nombrePDeshabilitado;
        this.fotoPDeshabilitado = fotoPDeshabilitado;
    }

    public String getNombrePDeshabilitado() {
        return nombrePDeshabilitado;
    }

    public void setNombrePDeshabilitado(String nombrePDeshabilitado) {
        this.nombrePDeshabilitado = nombrePDeshabilitado;
    }

    public String getFotoPDeshabilitado() {
        return fotoPDeshabilitado;
    }

    public void setFotoPDeshabilitado(String fotoPDeshabilitado) {
        this.fotoPDeshabilitado = fotoPDeshabilitado;
    }
}
