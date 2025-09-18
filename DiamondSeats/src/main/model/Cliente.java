package main.model;

import java.util.Objects;

/**
 * Represents a customer in the reservation system
 * Immutable class with proper equals and hashCode implementation
 */
public class Cliente {
    private final String name;
    private final String email;
    private final String phone;
    
    public Cliente(String name, String email, String phone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.phone = phone.trim();
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    /**
     * Returns a unique identifier for this customer
     */
    public String getCustomerId() {
        return email; // Using email as unique identifier
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Cliente cliente = (Cliente) obj;
        return Objects.equals(email, cliente.email); // Email uniquely identifies customer
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    
    @Override
    public String toString() {
        return String.format("Cliente{name='%s', email='%s', phone='%s'}", 
                           name, email, phone);
    }
}