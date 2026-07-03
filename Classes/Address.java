package Classes;

public class Address {
    private final String houseNumber;
    private final String postcode;
    private final String city;

    public Address(String houseNumber, String postcode, String city) {
        if (houseNumber == null || houseNumber.isBlank()) {
            throw new IllegalArgumentException("House number cannot be blank.");
        }
        if (postcode == null || postcode.isBlank()) {
            throw new IllegalArgumentException("Postcode cannot be blank.");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be blank.");
        }
        this.houseNumber = houseNumber.trim();
        this.postcode = postcode.trim();
        this.city = city.trim();
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", houseNumber, city, postcode);
    }
}
