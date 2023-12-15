package com.example.vocafe12.ui;

import java.io.Serializable;

public class VerPP implements Serializable {
    private String codigoP,IDCliente,fecha,hora;

    public VerPP() {
    }

    public VerPP(String codigoP, String IDCliente, String fecha, String hora) {
        this.codigoP = codigoP;
        this.IDCliente = IDCliente;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.IDCliente = nombreCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
