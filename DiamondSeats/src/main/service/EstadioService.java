package main.service;

import java.util.*;

public class EstadioService {
    private Map<String, Map<Integer, Map<Integer, Boolean>>> stadium; // section -> row -> seat -> isReserved
    private Map<String, Double> sectionPrices;
    private final int ROWS_PER_SECTION = 20;
    private final int SEATS_PER_ROW = 25;
    
    public EstadioService() {
        initializeStadium();
        initializePricing();
    }
    
    private void initializeStadium() {
        stadium = new HashMap<>();
        String[] sections = {"VIP", "PREMIUM", "GENERAL", "BLEACHERS"};
        
        for (String section : sections) {
            Map<Integer, Map<Integer, Boolean>> rows = new HashMap<>();
            for (int row = 1; row <= ROWS_PER_SECTION; row++) {
                Map<Integer, Boolean> seats = new HashMap<>();
                for (int seat = 1; seat <= SEATS_PER_ROW; seat++) {
                    seats.put(seat, false); // false = available, true = reserved
                }
                rows.put(row, seats);
            }
            stadium.put(section, rows);
        }
    }
    
    private void initializePricing() {
        sectionPrices = new HashMap<>();
        sectionPrices.put("VIP", 150.0);
        sectionPrices.put("PREMIUM", 100.0);
        sectionPrices.put("GENERAL", 50.0);
        sectionPrices.put("BLEACHERS", 25.0);
    }
    
    public boolean isSeatAvailable(String section, int row, int seat) {
        if (!isValidSeat(section, row, seat)) {
            return false;
        }
        return !stadium.get(section).get(row).get(seat);
    }
    
    public boolean reserveSeat(String section, int row, int seat) {
        if (!isSeatAvailable(section, row, seat)) {
            return false;
        }
        stadium.get(section).get(row).put(seat, true);
        return true;
    }
    
    public boolean cancelReservation(String section, int row, int seat) {
        if (!isValidSeat(section, row, seat)) {
            return false;
        }
        if (!stadium.get(section).get(row).get(seat)) {
            return false; // Seat wasn't reserved
        }
        stadium.get(section).get(row).put(seat, false);
        return true;
    }
    
    public boolean isValidSeat(String section, int row, int seat) {
        return stadium.containsKey(section.toUpperCase()) && 
               row >= 1 && row <= ROWS_PER_SECTION &&
               seat >= 1 && seat <= SEATS_PER_ROW;
    }
    
    public double getSectionPrice(String section) {
        return sectionPrices.getOrDefault(section.toUpperCase(), 0.0);
    }
    
    public Set<String> getAvailableSections() {
        return stadium.keySet();
    }
    
    public Map<String, Integer> getAvailableSeatsPerSection() {
        Map<String, Integer> available = new HashMap<>();
        for (String section : stadium.keySet()) {
            int count = 0;
            for (int row = 1; row <= ROWS_PER_SECTION; row++) {
                for (int seat = 1; seat <= SEATS_PER_ROW; seat++) {
                    if (!stadium.get(section).get(row).get(seat)) {
                        count++;
                    }
                }
            }
            available.put(section, count);
        }
        return available;
    }
    
    public List<String> getAvailableSeatsInSection(String section, int maxResults) {
        List<String> availableSeats = new ArrayList<>();
        if (!stadium.containsKey(section.toUpperCase())) {
            return availableSeats;
        }
        
        section = section.toUpperCase();
        for (int row = 1; row <= ROWS_PER_SECTION && availableSeats.size() < maxResults; row++) {
            for (int seat = 1; seat <= SEATS_PER_ROW && availableSeats.size() < maxResults; seat++) {
                if (!stadium.get(section).get(row).get(seat)) {
                    availableSeats.add("Fila " + row + ", Asiento " + seat);
                }
            }
        }
        return availableSeats;
    }
}
