package com.example.vocafe12.ui;

import java.io.Serializable;

public class VPedidos implements Serializable {
    private String codigoP,idCliente,fecha,hora;

    public VPedidos() {
    }

    public VPedidos(String codigoP, String idCliente, String fecha, String hora) {
        this.codigoP = codigoP;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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
