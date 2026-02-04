package edu.aitu.oop3.config;

public class DiscountManager {

    private static final DiscountManager INSTANCE = new DiscountManager();

    private double vipMultiplier = 1.30;
    private double studentDiscount = 0.20;

    private DiscountManager() {}

    public static DiscountManager getInstance() {
        return INSTANCE;
    }

    public double getVipMultiplier() { return vipMultiplier; }
    public double getStudentDiscount() { return studentDiscount; }

    public void setVipMultiplier(double vipMultiplier) { this.vipMultiplier = vipMultiplier; }
    public void setStudentDiscount(double studentDiscount) { this.studentDiscount = studentDiscount; }
}
