package task;

import java.time.LocalDate;

public class Transaction {
	private final LocalDate date;
    private final String provider;
    private final String size;
    private final double originalPrice;

    public Transaction(LocalDate date, String provider, String size, double originalPrice) {
        this.date = date;
        this.provider = provider;
        this.size = size;
        this.originalPrice = originalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProvider() {
        return provider;
    }

    public String getSize() {
        return size;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }
}
