package com.drivequest.model;

public class VehiculoPasajeros extends Vehiculo {
    private int numMaxPasajeros;

    public VehiculoPasajeros() {
        super();
    }

    public VehiculoPasajeros(String patente, String marca, String modelo, int anioFabricacion, double precioArriendoDia, int diasArrendado, int numMaxPasajeros) {
        super(patente, marca, modelo, anioFabricacion, precioArriendoDia, diasArrendado);
        this.numMaxPasajeros = numMaxPasajeros;
    }

    public int getNumMaxPasajeros() {
        return numMaxPasajeros;
    }

    public void setNumMaxPasajeros(int numMaxPasajeros) {
        this.numMaxPasajeros = numMaxPasajeros;
    }

    @Override
    public String mostrarDatos() {
        return "Tipo: Pasajeros\n" +
               "Patente: " + getPatente() + "\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Año: " + getAnioFabricacion() + "\n" +
               "Precio/día: $" + getPrecioArriendoDia() + "\n" +
               "Días arrendado: " + getDiasArrendado() + "\n" +
               "Máx. Pasajeros: " + numMaxPasajeros;
    }
}