package task;

import java.util.HashMap;
import java.util.List;

public class PricingUtils {
	
	private final List<HashMap<String, Object>> priceTable;

    public PricingUtils(List<HashMap<String, Object>> priceTable) {
        this.priceTable = priceTable;
    }

    public void addPrice(String provider, String size, double price) {
        HashMap<String, Object> item = new HashMap<>();
        item.put("provider", provider);
        item.put("size", size);
        item.put("price", price);
        priceTable.add(item);
    }

    public Double getPrice(String provider, String size) {
        for (HashMap<String, Object> priceItem : priceTable) {
            if (priceItem.get("provider").equals(provider) && 
                priceItem.get("size").equals(size)) {
                return (Double) priceItem.get("price");
            }
        }
        return null; 
    }
    
    public double getLowestPriceForSize(String size) {
        double lowestPrice = Double.MAX_VALUE;

        for (HashMap<String, Object> entry : priceTable) {
            if (entry.get("size").equals(size)) {
                double price = (double) entry.get("price");
                if (price < lowestPrice) {
                    lowestPrice = price;
                }
            }
        }

        if (lowestPrice == Double.MAX_VALUE) {
            return -1;
        }

        return lowestPrice;
    }

    public boolean providerExists(String provider) {
        for (HashMap<String, Object> priceItem : priceTable) {
            if (priceItem.get("provider").equals(provider)) {
                return true;
            }
        }
        return false;
    }

    public boolean sizeExists(String size) {
        for (HashMap<String, Object> priceItem : priceTable) {
            if (priceItem.get("size").equals(size)) {
                return true;
            }
        }
        return false;
    }

}
