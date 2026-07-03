package Classes;

public class PayPalPayment implements PaymentMethod {
    private final String email;

    public PayPalPayment(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("A valid PayPal email address is required.");
        }
        this.email = email.trim();
    }

    @Override
    public Receipt processPayment(double total, Address address) {
        return new Receipt(total, "PayPal (" + email + ")", address);
    }
}
