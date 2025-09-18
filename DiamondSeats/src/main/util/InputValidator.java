package main.util;

public class InputValidator {
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 2 &&
               name.matches("^[a-zA-ZÀ-ÿ\\s]+$");
    }
    
    public static boolean isValidSection(String section) {
        if (section == null) return false;
        String upperSection = section.toUpperCase();
        return upperSection.equals("VIP") || upperSection.equals("PREMIUM") || 
               upperSection.equals("GENERAL") || upperSection.equals("BLEACHERS");
    }
    
    public static boolean isValidRowNumber(int row) {
        return row >= 1 && row <= 20;
    }
    
    public static boolean isValidSeatNumber(int seat) {
        return seat >= 1 && seat <= 25;
    }
    
    public static boolean isValidReservationId(String id) {
        return id != null && id.matches("^RES\\d+$");
    }
    
    public static String sanitizeName(String name) {
        if (name == null) return "";
        return name.trim().replaceAll("\\s+", " ");
    }
    
    public static String sanitizeSection(String section) {
        if (section == null) return "";
        return section.trim().toUpperCase();
    }
    
    public static Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}