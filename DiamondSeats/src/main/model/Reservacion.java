package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a reservation linking a customer with their reserved seats
 */
public class Reservacion {
    private final String reservationId;
    private final Cliente cliente;
    private final List<Asiento> asientos;
    private final LocalDateTime reservationDate;
    private final double totalPrice;
    
    public Reservacion(String reservationId, Cliente cliente, List<Asiento> asientos) {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation ID cannot be null or empty");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente cannot be null");
        }
        if (asientos == null || asientos.isEmpty()) {
            throw new IllegalArgumentException("Seats list cannot be null or empty");
        }
        
        this.reservationId = reservationId;
        this.cliente = cliente;
        this.asientos = new ArrayList<>(asientos); // Defensive copy
        this.reservationDate = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
    }
    
    private double calculateTotalPrice() {
        return asientos.stream()
                      .mapToDouble(Asiento::getPrice)
                      .sum();
    }
    
    public String getReservationId() {
        return reservationId;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public List<Asiento> getAsientos() {
        return Collections.unmodifiableList(asientos);
    }
    
    public LocalDateTime getReservationDate() {
        return reservationDate;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public int getSeatCount() {
        return asientos.size();
    }
    
    /**
     * Gets the primary section of this reservation
     */
    public String getPrimarySection() {
        return asientos.isEmpty() ? "" : asientos.get(0).getSection();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Reservacion that = (Reservacion) obj;
        return Objects.equals(reservationId, that.reservationId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
    
    @Override
    public String toString() {
        return String.format("Reservacion{id='%s', cliente=%s, asientos=%d, total=$%.2f, fecha=%s}", 
                           reservationId, cliente.getName(), asientos.size(), 
                           totalPrice, reservationDate.toString());
    }
}