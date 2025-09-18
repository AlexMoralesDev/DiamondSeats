package main.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservationManager {
    private EstadioService estadioService;
    private Map<String, Reservation> reservations;
    private int reservationCounter;
    
    public ReservationManager(EstadioService estadioService) {
        this.estadioService = estadioService;
        this.reservations = new HashMap<>();
        this.reservationCounter = 1000;
    }
    
    public String makeReservation(String customerName, String section, int row, int seat) {
        if (customerName == null || customerName.trim().isEmpty()) {
            return null;
        }
        
        section = section.toUpperCase();
        if (!estadioService.reserveSeat(section, row, seat)) {
            return null;
        }
        
        String reservationId = "RES" + (++reservationCounter);
        double price = estadioService.getSectionPrice(section);
        
        Reservation reservation = new Reservation(
            reservationId, customerName.trim(), section, row, seat, price
        );
        
        reservations.put(reservationId, reservation);
        return reservationId;
    }
    
    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }
        
        boolean cancelled = estadioService.cancelReservation(
            reservation.getSection(), reservation.getRow(), reservation.getSeat()
        );
        
        if (cancelled) {
            reservations.remove(reservationId);
        }
        return cancelled;
    }
    
    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }
    
    public List<Reservation> getReservationsByCustomer(String customerName) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName.trim())) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    
    public EstadioService getEstadioService() {
        return estadioService;
    }
    
    public Collection<Reservation> getAllReservations() {
        return reservations.values();
    }
    
    // Inner class for Reservation
    public static class Reservation {
        private String reservationId;
        private String customerName;
        private String section;
        private int row;
        private int seat;
        private double price;
        private LocalDateTime reservationTime;
        
        public Reservation(String reservationId, String customerName, String section, 
                         int row, int seat, double price) {
            this.reservationId = reservationId;
            this.customerName = customerName;
            this.section = section;
            this.row = row;
            this.seat = seat;
            this.price = price;
            this.reservationTime = LocalDateTime.now();
        }
        
        // Getters
        public String getReservationId() { return reservationId; }
        public String getCustomerName() { return customerName; }
        public String getSection() { return section; }
        public int getRow() { return row; }
        public int getSeat() { return seat; }
        public double getPrice() { return price; }
        public LocalDateTime getReservationTime() { return reservationTime; }
        
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return String.format("ID: %s | Cliente: %s | %s Fila %d, Asiento %d | $%.2f | %s",
                reservationId, customerName, section, row, seat, price,
                reservationTime.format(formatter));
        }
    }
}