package com.example.appgastos;

public class Tarjeta {

    private String nombre;
    private String cantidad;
    private String uid;

    public Tarjeta() {

    }

    public Tarjeta(String uid, String nombre, String cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }

}