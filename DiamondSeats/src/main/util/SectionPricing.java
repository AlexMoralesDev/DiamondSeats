package main.util;

import java.util.HashMap;
import java.util.Map;

public class SectionPricing {
    private static final Map<String, Double> SECTION_PRICES = new HashMap<>();
    private static final Map<String, String> SECTION_DESCRIPTIONS = new HashMap<>();
    
    static {
        // Initialize pricing
        SECTION_PRICES.put("VIP", 150.0);
        SECTION_PRICES.put("PREMIUM", 100.0);
        SECTION_PRICES.put("GENERAL", 50.0);
        SECTION_PRICES.put("BLEACHERS", 25.0);
        
        // Initialize descriptions
        SECTION_DESCRIPTIONS.put("VIP", "Asientos VIP con servicio premium y mejor vista");
        SECTION_DESCRIPTIONS.put("PREMIUM", "Asientos premium con buena vista y comodidades");
        SECTION_DESCRIPTIONS.put("GENERAL", "Asientos generales con vista estándar");
        SECTION_DESCRIPTIONS.put("BLEACHERS", "Gradas populares, precio económico");
    }
    
    public static double getPrice(String section) {
        return SECTION_PRICES.getOrDefault(section.toUpperCase(), 0.0);
    }
    
    public static String getDescription(String section) {
        return SECTION_DESCRIPTIONS.getOrDefault(section.toUpperCase(), "Sección desconocida");
    }
    
    public static Map<String, Double> getAllPrices() {
        return new HashMap<>(SECTION_PRICES);
    }
    
    public static String formatPrice(double price) {
        return String.format("$%.2f", price);
    }
    
    public static void displayPricingMenu() {
        System.out.println("\n=== PRECIOS POR SECCIÓN ===");
        for (String section : SECTION_PRICES.keySet()) {
            System.out.printf("%-10s: %s - %s%n", 
                section, 
                formatPrice(SECTION_PRICES.get(section)),
                SECTION_DESCRIPTIONS.get(section));
        }
        System.out.println();
    }
}