package com.example.vocafe12.ui;

import java.io.Serializable;

public class PHabilitados implements Serializable {
    private String nombrePHabilitado,fotoPHabilitado;

    public PHabilitados() {
    }

    public PHabilitados(String nombrePHabilitado, String fotoPHabilitado) {
        this.nombrePHabilitado = nombrePHabilitado;
        this.fotoPHabilitado = fotoPHabilitado;
    }

    public String getNombrePHabilitado() {
        return nombrePHabilitado;
    }

    public void setNombrePHabilitado(String nombrePHabilitado) {
        this.nombrePHabilitado = nombrePHabilitado;
    }

    public String getFotoPHabilitado() {
        return fotoPHabilitado;
    }

    public void setFotoPHabilitado(String fotoPHabilitado) {
        this.fotoPHabilitado = fotoPHabilitado;
    }
}
