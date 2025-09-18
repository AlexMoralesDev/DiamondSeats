package main;

import main.service.EstadioService;
import main.service.ReservationManager;
import main.ui.ConsoleInterface;

/**
 * Main entry point for the Baseball Field Seat Reservation System
 * Initializes all services and starts the console interface
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE RESERVACIONES DE ASIENTOS DE BASEBALL ===");
        System.out.println("Inicializando sistema...\n");
        
        try {
            // Initialize core services
            EstadioService estadioService = new EstadioService();
            ReservationManager reservationManager = new ReservationManager(estadioService);
            
            // Start the console interface
            ConsoleInterface ui = new ConsoleInterface(reservationManager);
            ui.start();
            
        } catch (Exception e) {
            System.err.println("Error al inicializar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}