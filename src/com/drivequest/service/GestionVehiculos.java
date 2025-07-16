package com.drivequest.service;

import com.drivequest.model.Vehiculo;
import com.drivequest.util.Calculable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GestionVehiculos implements Calculable {
    private List<Vehiculo> flotaVehiculos;
    private final String NOMBRE_ARCHIVO = "vehiculos.dat";

    public GestionVehiculos() {
        this.flotaVehiculos = Collections.synchronizedList(new ArrayList<>());
        cargarVehiculosDesdeArchivo(); 
    }

    public void agregarVehiculo(Vehiculo vehiculo) throws IllegalArgumentException {
        synchronized (flotaVehiculos) {
            if (flotaVehiculos.contains(vehiculo)) {
                throw new IllegalArgumentException("Error: La patente " + vehiculo.getPatente() + " ya existe en la flota.");
            }
            flotaVehiculos.add(vehiculo);
            guardarVehiculosEnArchivo(); 
            System.out.println("Vehículo " + vehiculo.getPatente() + " agregado exitosamente.");
        }
    }

    public String listarVehiculos() {
        if (flotaVehiculos.isEmpty()) {
            return "No hay vehículos registrados en la flota.";
        }
        StringBuilder sb = new StringBuilder();
        synchronized (flotaVehiculos) { 
            for (Vehiculo v : flotaVehiculos) {
                sb.append(v.mostrarDatos()).append("\n--------------------\n");
            }
        }
        return sb.toString();
    }

    public int obtenerVehiculosArriendosLargos() {
        AtomicInteger count = new AtomicInteger(0);
        synchronized (flotaVehiculos) {
            for (Vehiculo v : flotaVehiculos) {
                if (v.getDiasArrendado() >= 7) {
                    count.incrementAndGet();
                }
            }
        }
        return count.get();
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        String patenteNormalizada = patente.replace("-", "").toUpperCase(); 
        synchronized (flotaVehiculos) {
            for (Vehiculo v : flotaVehiculos) {
                if (v.getPatente().equalsIgnoreCase(patenteNormalizada)) { 
                    return v;
                }
            }
        }
        return null;
    }

    private void guardarVehiculosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            synchronized (flotaVehiculos) {
                oos.writeObject(flotaVehiculos);
            }
            System.out.println("Datos de vehículos guardados exitosamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar vehículos en el archivo: " + e.getMessage());
        }
    }

    private void cargarVehiculosDesdeArchivo() {
        File file = new File(NOMBRE_ARCHIVO);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    flotaVehiculos = Collections.synchronizedList((List<Vehiculo>) obj);
                    System.out.println("Datos de vehículos cargados exitosamente desde " + NOMBRE_ARCHIVO);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar vehículos desde el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Archivo de datos no encontrado. Se iniciará con una flota vacía.");
        }
    }

    public List<Vehiculo> getFlotaVehiculos() {
        synchronized (flotaVehiculos) {
            return new ArrayList<>(flotaVehiculos);
        }
    }

    public void cargarVehiculosConcurrente(List<Vehiculo> vehiculosALoad) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 
        
        System.out.println("\nIniciando carga concurrente de vehículos...");
        for (Vehiculo vehiculo : vehiculosALoad) {
            executor.submit(() -> {
                try {
                    agregarVehiculo(vehiculo); 
                } catch (IllegalArgumentException e) {
                    System.err.println("Error en hilo al agregar " + vehiculo.getPatente() + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error inesperado en hilo: " + e.getMessage());
                }
            });
        }

        executor.shutdown(); 
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Algunas tareas de carga no terminaron a tiempo.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("Proceso de carga concurrente interrumpido.");
        }
        System.out.println("Carga concurrente de vehículos finalizada.");
        guardarVehiculosEnArchivo(); 
    }
}