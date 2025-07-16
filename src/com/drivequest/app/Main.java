package com.drivequest.app;

import com.drivequest.model.Vehiculo;
import com.drivequest.model.VehiculoCarga;
import com.drivequest.model.VehiculoPasajeros;
import com.drivequest.service.GestionVehiculos;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList; 
import java.util.List; 

public class Main {

    public static void main(String[] args) {
        GestionVehiculos gestion = new GestionVehiculos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Principal DriveQuest Rentals ---");
            System.out.println("1. Agregar Vehículo");
            System.out.println("2. Listar Todos los Vehículos");
            System.out.println("3. Mostrar Boleta de Arriendo (por Patente)");
            System.out.println("4. Cantidad de Vehículos con Arriendos Largos (>= 7 días)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        agregarVehiculo(scanner, gestion);
                        break;
                    case 2:
                        System.out.println("\n--- Lista de Vehículos ---");
                        System.out.println(gestion.listarVehiculos());
                        break;
                    case 3:
                        mostrarBoleta(scanner, gestion);
                        break;
                    case 4:
                        System.out.println("\n--- Vehículos con Arriendos Largos ---");
                        int cantidad = gestion.obtenerVehiculosArriendosLargos();
                        System.out.println("Hay " + cantidad + " vehículos con arriendos de 7 o más días.");
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema. ¡Gracias por usar DriveQuest Rentals!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); 
                opcion = -1; 
            } catch (Exception e) {
                System.err.println("Ocurrió un error inesperado: " + e.getMessage());
                opcion = -1;
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void agregarVehiculo(Scanner scanner, GestionVehiculos gestion) {
        System.out.println("\n--- Agregar Nuevo Vehículo ---");
        String patente = "";
        String regexPatente = "(^[A-Z]{4}-\\d{2}$)|(^[A-Z]{2}-\\d{4}$)"; 

        while (true) {
            System.out.print("Ingrese patente (Ej: ABCD-12 o AB-1234): ");
            patente = scanner.nextLine().trim().toUpperCase(); 
            
            if (patente.isEmpty()) {
                System.err.println("La patente no puede estar vacía.");
            } else if (!patente.matches(regexPatente)) { 
                System.err.println("Formato de patente inválido. Use 4 letras, guion y 2 números (Ej: ABCD-12) O 2 letras, guion y 4 números (Ej: AB-1234).");
            } else {
                if (gestion.buscarVehiculoPorPatente(patente) != null) { 
                    System.err.println("Error: Ya existe un vehículo con la patente " + patente + ". Intente con otra.");
                } else {
                    break; 
                }
            }
        }
        
        System.out.print("Ingrese marca: ");
        String marca = scanner.nextLine();
        System.out.print("Ingrese modelo: ");
        String modelo = scanner.nextLine();
        int anio = 0;
        while(true) {
            try {
                System.out.print("Ingrese año de fabricación: ");
                anio = scanner.nextInt();
                if (anio <= 1900 || anio > 2025) { 
                    System.err.println("Año de fabricación inválido. Debe ser un año razonable.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número para el año.");
                scanner.nextLine(); 
            }
        }
        
        double precioDia = 0;
        while(true) {
            try {
                System.out.print("Ingrese precio de arriendo por día: ");
                precioDia = scanner.nextDouble();
                if (precioDia <= 0) {
                     System.err.println("El precio por día debe ser un valor positivo.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número para el precio.");
                scanner.nextLine();
            }
        }

        int diasArrendado = 0;
        while(true) {
            try {
                System.out.print("Ingrese días arrendado: ");
                diasArrendado = scanner.nextInt();
                if (diasArrendado < 0) {
                     System.err.println("Los días arrendados no pueden ser negativos.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número entero para los días.");
                scanner.nextLine();
            }
        }
        scanner.nextLine(); 

        System.out.print("¿Es vehículo de Carga (C) o Pasajeros (P)? ");
        String tipo = scanner.nextLine().trim().toUpperCase();

        try {
            if (tipo.equals("C")) {
                double capacidadCarga = 0;
                while(true) {
                    try {
                        System.out.print("Ingrese capacidad de carga (KG): ");
                        capacidadCarga = scanner.nextDouble();
                        if (capacidadCarga <= 0) {
                            System.err.println("La capacidad de carga debe ser un valor positivo.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Entrada inválida. Por favor, ingrese un número para la capacidad.");
                        scanner.nextLine();
                    }
                }
                scanner.nextLine(); 
                VehiculoCarga vc = new VehiculoCarga(patente, marca, modelo, anio, precioDia, diasArrendado, capacidadCarga);
                gestion.agregarVehiculo(vc);
            } else if (tipo.equals("P")) {
                int numPasajeros = 0;
                while(true) {
                    try {
                        System.out.print("Ingrese número máximo de pasajeros: ");
                        numPasajeros = scanner.nextInt();
                        if (numPasajeros <= 0) {
                            System.err.println("El número de pasajeros debe ser un valor positivo.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Entrada inválida. Por favor, ingrese un número entero para los pasajeros.");
                        scanner.nextLine();
                    }
                }
                scanner.nextLine(); 
                VehiculoPasajeros vp = new VehiculoPasajeros(patente, marca, modelo, anio, precioDia, diasArrendado, numPasajeros);
                gestion.agregarVehiculo(vp);
            } else {
                System.err.println("Tipo de vehículo no válido. No se agregó el vehículo.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar vehículo: " + e.getMessage());
        }
    }

    private static void mostrarBoleta(Scanner scanner, GestionVehiculos gestion) {
        System.out.println("\n--- Mostrar Boleta de Arriendo ---");
        System.out.print("Ingrese la patente del vehículo: ");
        String patente = scanner.nextLine().trim();

        try {
            Vehiculo vehiculo = gestion.buscarVehiculoPorPatente(patente);
            if (vehiculo != null) {
                System.out.println(gestion.calcularBoleta(vehiculo));
            } else {
                System.err.println("No se encontró ningún vehículo con la patente: " + patente);
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar boleta: " + e.getMessage());
        }
    }
}