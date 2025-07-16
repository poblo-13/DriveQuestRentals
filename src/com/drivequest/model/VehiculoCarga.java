package com.drivequest.model;

public class VehiculoCarga extends Vehiculo {
    private double capacidadCargaKG;

    public VehiculoCarga() {
        super();
    }

    public VehiculoCarga(String patente, String marca, String modelo, int anioFabricacion, double precioArriendoDia, int diasArrendado, double capacidadCargaKG) {
        super(patente, marca, modelo, anioFabricacion, precioArriendoDia, diasArrendado);
        this.capacidadCargaKG = capacidadCargaKG;
    }

    public double getCapacidadCargaKG() {
        return capacidadCargaKG;
    }

    public void setCapacidadCargaKG(double capacidadCargaKG) {
        this.capacidadCargaKG = capacidadCargaKG;
    }

    @Override
    public String mostrarDatos() {
        return "Tipo: Carga\n" +
               "Patente: " + getPatente() + "\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Año: " + getAnioFabricacion() + "\n" +
               "Precio/día: $" + getPrecioArriendoDia() + "\n" +
               "Días arrendado: " + getDiasArrendado() + "\n" +
               "Capacidad de Carga: " + capacidadCargaKG + " KG";
    }
}