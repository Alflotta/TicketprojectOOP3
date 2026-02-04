package edu.aitu.oop3.tickets;

import edu.aitu.oop3.config.DiscountManager;

public class TicketFactory {

    public static double calculatePrice(TicketType type, double basePrice) {
        DiscountManager dm = DiscountManager.getInstance();
        return switch (type) {
            case STANDARD -> basePrice;
            case VIP -> basePrice * dm.getVipMultiplier();
            case STUDENT -> basePrice * (1.0 - dm.getStudentDiscount());
        };
    }
}
