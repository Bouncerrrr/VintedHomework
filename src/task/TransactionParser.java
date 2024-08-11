package task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TransactionParser {
	private final PricingUtils pricingUtils;

    public TransactionParser(PricingUtils pricingUtils) {
        this.pricingUtils = pricingUtils;
    }

    public Transaction parseLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            return null;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(parts[0]);
        } catch (DateTimeParseException e) {
            return null;
        }

        String size = parts[1];
        String provider = parts[2];

        if (!pricingUtils.providerExists(provider) || !pricingUtils.sizeExists(size)) {
            return null;
        }
        
        Double price = pricingUtils.getPrice(provider, size);
        
        if (price == null) {
            return null; 
        }

        return new Transaction(date, provider, size, price);
    }
    
}
