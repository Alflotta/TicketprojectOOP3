package edu.aitu.oop3.config;

import java.util.HashMap;
import java.util.Map;

public class DiscountManager {
    private static DiscountManager instance;

    // Мысал: ticketType -> discount percent (0.10 = 10%)
    private final Map<String, Double> discounts = new HashMap<>();

    private DiscountManager() {
        discounts.put("STANDARD", 0.00);
        discounts.put("VIP", 0.05);
        discounts.put("STUDENT", 0.15);
    }

    public static DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }

    public double getDiscount(String ticketType) {
        return discounts.getOrDefault(ticketType.toUpperCase(), 0.0);
    }

    public void setDiscount(String ticketType, double percent) {
        discounts.put(ticketType.toUpperCase(), percent);
    }
}
