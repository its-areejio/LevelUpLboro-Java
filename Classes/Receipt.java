package Classes;

import java.time.LocalDate;

public class Receipt {
    private final double total;
    private final String paymentMethod;
    private final Address address;
    private final LocalDate date;

    public Receipt(double total, String paymentMethod, Address address) {
        if (total < 0) {
            throw new IllegalArgumentException("Total must not be negative.");
        }
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("Payment method cannot be blank.");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        this.total = total;
        this.paymentMethod = paymentMethod.trim();
        this.address = address;
        this.date = LocalDate.now();
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Receipt:%n  Date: %s%n  Payment: %s%n  Total: £%.2f%n  Billing address: %s",
                date, paymentMethod, total, address);
    }
}
