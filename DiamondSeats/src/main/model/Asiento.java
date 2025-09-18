package main.model;

import java.util.Objects;

/**
 * Represents a seat in the baseball stadium
 * Immutable class with proper equals and hashCode implementation
 */
public class Asiento {
    private final String section;
    private final int row;
    private final int seatNumber;
    private final double price;
    
    public Asiento(String section, int row, int seatNumber, double price) {
        if (section == null || section.trim().isEmpty()) {
            throw new IllegalArgumentException("Section cannot be null or empty");
        }
        if (row <= 0 || seatNumber <= 0) {
            throw new IllegalArgumentException("Row and seat number must be positive");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        
        this.section = section;
        this.row = row;
        this.seatNumber = seatNumber;
        this.price = price;
    }
    
    public String getSection() {
        return section;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public double getPrice() {
        return price;
    }
    
    /**
     * Returns a unique identifier for this seat
     */
    public String getSeatId() {
        return section + "-" + row + "-" + seatNumber;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Asiento asiento = (Asiento) obj;
        return row == asiento.row &&
               seatNumber == asiento.seatNumber &&
               Double.compare(asiento.price, price) == 0 &&
               Objects.equals(section, asiento.section);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(section, row, seatNumber, price);
    }
    
    @Override
    public String toString() {
        return String.format("Asiento{%s, Fila=%d, Asiento=%d, Precio=$%.2f}", 
                           section, row, seatNumber, price);
    }
}