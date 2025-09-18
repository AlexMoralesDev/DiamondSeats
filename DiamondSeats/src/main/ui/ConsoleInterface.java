package main.ui;

import main.service.ReservationManager;
import main.service.ReservationManager.Reservation;
import main.util.InputValidator;
import main.util.SectionPricing;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Collection;

public class ConsoleInterface {
    private ReservationManager reservationManager;
    private Scanner scanner;
    
    public ConsoleInterface(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("¡Bienvenido al Sistema de Reservaciones de Baseball!");
        
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    makeReservation();
                    break;
                case "2":
                    cancelReservation();
                    break;
                case "3":
                    viewReservation();
                    break;
                case "4":
                    viewAvailableSeats();
                    break;
                case "5":
                    viewCustomerReservations();
                    break;
                case "6":
                    viewAllReservations();
                    break;
                case "7":
                    SectionPricing.displayPricingMenu();
                    break;
                case "8":
                    System.out.println("¡Gracias por usar el sistema de reservaciones!");
                    return;
                default:
                    System.out.println("Opción inválida. Por favor seleccione una opción válida.");
            }
            
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + repeatString("=", 50));
        System.out.println("         SISTEMA DE RESERVACIONES");
        System.out.println(repeatString("=", 50));
        System.out.println("1. Hacer una reservación");
        System.out.println("2. Cancelar reservación");
        System.out.println("3. Ver detalles de reservación");
        System.out.println("4. Ver asientos disponibles");
        System.out.println("5. Ver reservaciones por cliente");
        System.out.println("6. Ver todas las reservaciones");
        System.out.println("7. Ver precios por sección");
        System.out.println("8. Salir");
        System.out.println(repeatString("=", 50));
        System.out.print("Seleccione una opción (1-8): ");
    }
    
    // Helper method for Java compatibility (replaces String.repeat())
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    private void makeReservation() {
        System.out.println("\n=== HACER RESERVACIÓN ===");
        
        // Get customer name
        String customerName;
        do {
            System.out.print("Nombre del cliente: ");
            customerName = scanner.nextLine().trim();
            if (!InputValidator.isValidName(customerName)) {
                System.out.println("Nombre inválido. Debe contener al menos 2 caracteres y solo letras.");
            }
        } while (!InputValidator.isValidName(customerName));
        
        // Show available sections
        SectionPricing.displayPricingMenu();
        
        // Get section
        String section;
        do {
            System.out.print("Sección (VIP/PREMIUM/GENERAL/BLEACHERS): ");
            section = scanner.nextLine().trim().toUpperCase();
            if (!InputValidator.isValidSection(section)) {
                System.out.println("Sección inválida. Opciones: VIP, PREMIUM, GENERAL, BLEACHERS");
            }
        } while (!InputValidator.isValidSection(section));
        
        // Show some available seats in the section
        List<String> availableSeats = reservationManager.getEstadioService()
            .getAvailableSeatsInSection(section, 10);
        if (availableSeats.isEmpty()) {
            System.out.println("Lo siento, no hay asientos disponibles en la sección " + section);
            return;
        }
        
        System.out.println("\nAlgunos asientos disponibles en " + section + ":");
        for (int i = 0; i < Math.min(5, availableSeats.size()); i++) {
            System.out.println("- " + availableSeats.get(i));
        }
        
        // Get row
        int row;
        do {
            System.out.print("Número de fila (1-20): ");
            Integer rowInput = InputValidator.parseInteger(scanner.nextLine());
            if (rowInput == null || !InputValidator.isValidRowNumber(rowInput)) {
                System.out.println("Fila inválida. Debe ser un número entre 1 y 20.");
                row = -1;
            } else {
                row = rowInput;
            }
        } while (row == -1);
        
        // Get seat
        int seat;
        do {
            System.out.print("Número de asiento (1-25): ");
            Integer seatInput = InputValidator.parseInteger(scanner.nextLine());
            if (seatInput == null || !InputValidator.isValidSeatNumber(seatInput)) {
                System.out.println("Asiento inválido. Debe ser un número entre 1 y 25.");
                seat = -1;
            } else {
                seat = seatInput;
            }
        } while (seat == -1);
        
        // Check if seat is available
        if (!reservationManager.getEstadioService().isSeatAvailable(section, row, seat)) {
            System.out.println("Lo siento, ese asiento no está disponible.");
            return;
        }
        
        // Show price and confirm
        double price = reservationManager.getEstadioService().getSectionPrice(section);
        System.out.printf("\nResumen de la reservación:");
        System.out.printf("\nCliente: %s", customerName);
        System.out.printf("\nUbicación: %s, Fila %d, Asiento %d", section, row, seat);
        System.out.printf("\nPrecio: $%.2f", price);
        System.out.print("\n¿Confirmar reservación? (s/n): ");
        
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("s") && !confirm.equals("si")) {
            System.out.println("Reservación cancelada.");
            return;
        }
        
        // Make reservation
        String reservationId = reservationManager.makeReservation(customerName, section, row, seat);
        if (reservationId != null) {
            System.out.println("\n¡Reservación exitosa!");
            System.out.println("ID de reservación: " + reservationId);
            System.out.println("Guarde este ID para futuras consultas.");
        } else {
            System.out.println("Error al procesar la reservación. Inténtelo nuevamente.");
        }
    }
    
    private void cancelReservation() {
        System.out.println("\n=== CANCELAR RESERVACIÓN ===");
        System.out.print("ID de reservación: ");
        String reservationId = scanner.nextLine().trim();
        
        if (!InputValidator.isValidReservationId(reservationId)) {
            System.out.println("ID de reservación inválido.");
            return;
        }
        
        Reservation reservation = reservationManager.getReservation(reservationId);
        if (reservation == null) {
            System.out.println("Reservación no encontrada.");
            return;
        }
        
        System.out.println("\nDetalles de la reservación:");
        System.out.println(reservation.toString());
        System.out.print("\n¿Está seguro de cancelar esta reservación? (s/n): ");
        
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("s") || confirm.equals("si")) {
            if (reservationManager.cancelReservation(reservationId)) {
                System.out.println("Reservación cancelada exitosamente.");
            } else {
                System.out.println("Error al cancelar la reservación.");
            }
        } else {
            System.out.println("Cancelación abortada.");
        }
    }
    
    private void viewReservation() {
        System.out.println("\n=== VER DETALLES DE RESERVACIÓN ===");
        System.out.print("ID de reservación: ");
        String reservationId = scanner.nextLine().trim();
        
        Reservation reservation = reservationManager.getReservation(reservationId);
        if (reservation == null) {
            System.out.println("Reservación no encontrada.");
            return;
        }
        
        System.out.println("\nDetalles de la reservación:");
        System.out.println(repeatString("=", 40));
        System.out.println(reservation.toString());
    }
    
    private void viewAvailableSeats() {
        System.out.println("\n=== ASIENTOS DISPONIBLES ===");
        
        Map<String, Integer> availableSeats = reservationManager.getEstadioService()
            .getAvailableSeatsPerSection();
        
        System.out.println("Asientos disponibles por sección:");
        System.out.println(repeatString("=", 35));
        for (String section : availableSeats.keySet()) {
            int available = availableSeats.get(section);
            double price = reservationManager.getEstadioService().getSectionPrice(section);
            System.out.printf("%-10s: %3d asientos (Precio: $%.2f)%n", 
                section, available, price);
        }
        
        System.out.print("\n¿Ver asientos específicos de alguna sección? (s/n): ");
        String viewSpecific = scanner.nextLine().trim().toLowerCase();
        
        if (viewSpecific.equals("s") || viewSpecific.equals("si")) {
            System.out.print("Sección (VIP/PREMIUM/GENERAL/BLEACHERS): ");
            String section = scanner.nextLine().trim().toUpperCase();
            
            if (InputValidator.isValidSection(section)) {
                List<String> seats = reservationManager.getEstadioService()
                    .getAvailableSeatsInSection(section, 20);
                
                if (seats.isEmpty()) {
                    System.out.println("No hay asientos disponibles en " + section);
                } else {
                    System.out.println("\nAsientos disponibles en " + section + ":");
                    for (int i = 0; i < Math.min(20, seats.size()); i++) {
                        System.out.println((i + 1) + ". " + seats.get(i));
                    }
                    if (seats.size() > 20) {
                        System.out.println("... y " + (seats.size() - 20) + " más.");
                    }
                }
            } else {
                System.out.println("Sección inválida.");
            }
        }
    }
    
    private void viewCustomerReservations() {
        System.out.println("\n=== RESERVACIONES POR CLIENTE ===");
        System.out.print("Nombre del cliente: ");
        String customerName = scanner.nextLine().trim();
        
        List<Reservation> reservations = reservationManager.getReservationsByCustomer(customerName);
        
        if (reservations.isEmpty()) {
            System.out.println("No se encontraron reservaciones para: " + customerName);
            return;
        }
        
        System.out.println("\nReservaciones de " + customerName + ":");
        System.out.println(repeatString("=", 70));
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
        System.out.println("\nTotal de reservaciones: " + reservations.size());
    }
    
    private void viewAllReservations() {
        System.out.println("\n=== TODAS LAS RESERVACIONES ===");
        
        Collection<Reservation> allReservations = reservationManager.getAllReservations();
        
        if (allReservations.isEmpty()) {
            System.out.println("No hay reservaciones en el sistema.");
            return;
        }
        
        System.out.println("Total de reservaciones: " + allReservations.size());
        System.out.println(repeatString("=", 70));
        for (Reservation reservation : allReservations) {
            System.out.println(reservation.toString());
        }
    }
}