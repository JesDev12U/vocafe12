package com.example.vocafe12.ui;

import java.io.Serializable;

public class DesbloquearClientesClass implements Serializable {
    private String IDDC,nombreCompletoDC,correoDC,passwordDC;

    public DesbloquearClientesClass() {
    }

    public DesbloquearClientesClass(String IDDC, String nombreCompletoDC, String correoDC, String passwordDC) {
        this.IDDC = IDDC;
        this.nombreCompletoDC = nombreCompletoDC;
        this.correoDC = correoDC;
        this.passwordDC = passwordDC;
    }

    public String getIDDC() {
        return IDDC;
    }

    public void setIDDC(String IDDC) {
        this.IDDC = IDDC;
    }

    public String getNombreCompletoDC() {
        return nombreCompletoDC;
    }

    public void setNombreCompletoDC(String nombreCompletoDC) {
        this.nombreCompletoDC = nombreCompletoDC;
    }

    public String getCorreoDC() {
        return correoDC;
    }

    public void setCorreoDC(String correoDC) {
        this.correoDC = correoDC;
    }

    public String getPasswordDC() {
        return passwordDC;
    }

    public void setPasswordDC(String passwordDC) {
        this.passwordDC = passwordDC;
    }
}
