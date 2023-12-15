package com.example.vocafe12.ui;

import java.io.Serializable;

public class Carrito implements Serializable {
    private String nombreProducto,detalles,cantidad,total,foto;

    public Carrito() {
    }

    public Carrito(String nombreProducto, String detalles, String cantidad, String total, String foto) {
        this.nombreProducto = nombreProducto;
        this.detalles = detalles;
        this.cantidad = cantidad;
        this.total = total;
        this.foto = foto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
