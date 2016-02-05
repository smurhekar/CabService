package main;

/**
 * Created by idnsmu on 2/4/2016.
 */
public class Bill {

    private final Double billingRate;

    public Bill(Double billingRate) {
        this.billingRate = billingRate;
    }

    public Double calculate(Double distance) {
        return billingRate * distance;
    }
}
