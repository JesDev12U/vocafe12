package com.example.vocafe12.ui;

public class DetallesPPSEmp {
    private String nombreProductoPP,cantidadPP,importePP,detallesPP,fotoPP;

    public DetallesPPSEmp() {
    }

    public DetallesPPSEmp(String nombreProductoPP, String cantidadPP, String importePP, String detallesPP, String fotoPP) {
        this.nombreProductoPP = nombreProductoPP;
        this.cantidadPP = cantidadPP;
        this.importePP = importePP;
        this.detallesPP = detallesPP;
        this.fotoPP = fotoPP;
    }

    public String getNombreProductoPP() {
        return nombreProductoPP;
    }

    public void setNombreProductoPP(String nombreProductoPP) {
        this.nombreProductoPP = nombreProductoPP;
    }

    public String getCantidadPP() {
        return cantidadPP;
    }

    public void setCantidadPP(String cantidadPP) {
        this.cantidadPP = cantidadPP;
    }

    public String getImportePP() {
        return importePP;
    }

    public void setImportePP(String importePP) {
        this.importePP = importePP;
    }

    public String getDetallesPP() {
        return detallesPP;
    }

    public void setDetallesPP(String detallesPP) {
        this.detallesPP = detallesPP;
    }

    public String getFotoPP() {
        return fotoPP;
    }

    public void setFotoPP(String fotoPP) {
        this.fotoPP = fotoPP;
    }
}
