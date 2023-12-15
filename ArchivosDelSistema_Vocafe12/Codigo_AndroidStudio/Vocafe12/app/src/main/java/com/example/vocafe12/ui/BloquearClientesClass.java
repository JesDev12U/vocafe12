package com.example.vocafe12.ui;

import java.io.Serializable;

public class BloquearClientesClass implements Serializable {
    private String IDBC,nombreCompletoBC,correoBC,PasswordBC;

    public BloquearClientesClass() {
    }

    public BloquearClientesClass(String IDBC, String nombreCompletoBC, String correoBC, String passwordBC) {
        this.IDBC = IDBC;
        this.nombreCompletoBC = nombreCompletoBC;
        this.correoBC = correoBC;
        PasswordBC = passwordBC;
    }

    public String getIDBC() {
        return IDBC;
    }

    public void setIDBC(String IDBC) {
        this.IDBC = IDBC;
    }

    public String getNombreCompletoBC() {
        return nombreCompletoBC;
    }

    public void setNombreCompletoBC(String nombreCompletoBC) {
        this.nombreCompletoBC = nombreCompletoBC;
    }

    public String getCorreoBC() {
        return correoBC;
    }

    public void setCorreoBC(String correoBC) {
        this.correoBC = correoBC;
    }

    public String getPasswordBC() {
        return PasswordBC;
    }

    public void setPasswordBC(String passwordBC) {
        PasswordBC = passwordBC;
    }
}
