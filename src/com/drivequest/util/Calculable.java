package com.drivequest.util;

import com.drivequest.model.Vehiculo;
import com.drivequest.model.VehiculoCarga;
import com.drivequest.model.VehiculoPasajeros;

public interface Calculable {
    double IVA = 0.19; 
    double DESCUENTO_CARGA = 0.07; 
    double DESCUENTO_PASAJEROS = 0.12; 

    default String calcularBoleta(Vehiculo vehiculo) {
        double subtotal = vehiculo.getPrecioArriendoDia() * vehiculo.getDiasArrendado();
        double ivaMonto = subtotal * IVA;
        double descuentoMonto = 0;
        String tipoDescuento = "N/A";

        if (vehiculo instanceof VehiculoCarga) {
            descuentoMonto = subtotal * DESCUENTO_CARGA;
            tipoDescuento = "Carga (" + (DESCUENTO_CARGA * 100) + "%)";
        } else if (vehiculo instanceof VehiculoPasajeros) {
            descuentoMonto = subtotal * DESCUENTO_PASAJEROS;
            tipoDescuento = "Pasajeros (" + (DESCUENTO_PASAJEROS * 100) + "%)";
        }

        double totalConIVA = subtotal + ivaMonto;
        double totalAPagar = totalConIVA - descuentoMonto;

        StringBuilder sb = new StringBuilder();
        sb.append("--- Detalle de Boleta ---").append("\n");
        sb.append("Vehículo: ").append(vehiculo.getPatente()).append(" (").append(vehiculo.getMarca()).append(" ").append(vehiculo.getModelo()).append(")").append("\n");
        sb.append("Precio por día: $").append(String.format("%.2f", vehiculo.getPrecioArriendoDia())).append("\n");
        sb.append("Días arrendado: ").append(vehiculo.getDiasArrendado()).append("\n");
        sb.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("IVA (").append(String.format("%.0f", IVA * 100)).append("%): $").append(String.format("%.2f", ivaMonto)).append("\n");
        sb.append("Descuento (").append(tipoDescuento).append("): $").append(String.format("%.2f", descuentoMonto)).append("\n");
        sb.append("Total a Pagar: $").append(String.format("%.2f", totalAPagar)).append("\n");
        sb.append("------------------------").append("\n");

        return sb.toString();
    }
}