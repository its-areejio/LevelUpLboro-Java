package Classes;

public class CardPayment implements PaymentMethod {
    private final String cardNumber;
    private final String securityCode;

    public CardPayment(String cardNumber, String securityCode) {
        if (cardNumber == null || cardNumber.isBlank() || !cardNumber.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("Card number must be between 13 and 19 digits.");
        }
        if (securityCode == null || securityCode.isBlank() || !securityCode.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("Security code must be 3 or 4 digits.");
        }
        this.cardNumber = cardNumber.trim();
        this.securityCode = securityCode.trim();
    }

    @Override
    public Receipt processPayment(double total, Address address) {
        return new Receipt(total, "Card ending " + cardNumber.substring(cardNumber.length() - 4), address);
    }
}
