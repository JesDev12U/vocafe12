package com.example.vocafe12.ui;

import java.io.Serializable;

public class PedidoPendiente implements Serializable {
    private String codigoP,fecha,hora,statusPedido;

    public PedidoPendiente() {
    }

    public PedidoPendiente(String codigoP, String fecha, String hora, String statusPedido) {
        this.codigoP = codigoP;
        this.fecha = fecha;
        this.hora = hora;
        this.statusPedido = statusPedido;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
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

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }
}
