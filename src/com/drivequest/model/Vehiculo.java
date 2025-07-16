package com.drivequest.model;

import java.io.Serializable;

public abstract class Vehiculo implements Serializable {
    private String patente;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private double precioArriendoDia;
    private int diasArrendado;

    public Vehiculo() {
    }

    public Vehiculo(String patente, String marca, String modelo, int anioFabricacion, double precioArriendoDia, int diasArrendado) {
        this.patente = patente.replace("-", "").toUpperCase();
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.precioArriendoDia = precioArriendoDia;
        this.diasArrendado = diasArrendado;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public double getPrecioArriendoDia() {
        return precioArriendoDia;
    }

    public int getDiasArrendado() {
        return diasArrendado;
    }

    public void setPatente(String patente) {
        this.patente = patente.replace("-", "").toUpperCase();
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public void setPrecioArriendoDia(double precioArriendoDia) {
        this.precioArriendoDia = precioArriendoDia;
    }

    public void setDiasArrendado(int diasArrendado) {
        this.diasArrendado = diasArrendado;
    }

    public abstract String mostrarDatos();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) obj;
        return patente != null ? patente.equals(vehiculo.patente) : vehiculo.patente == null;
    }

    @Override
    public int hashCode() {
        return patente != null ? patente.hashCode() : 0;
    }
}