package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PricingUtilsTest {

	@Test
	void testAddPrice() {
		
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);

        assertEquals(1, priceTable.size());
        assertEquals("Provider", priceTable.get(0).get("provider"));
        assertEquals("L", priceTable.get(0).get("size"));
        assertEquals(5.0, priceTable.get(0).get("price"));
    }

	@Test
	void testGetPrice_ExistingEntry() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);
        Double price = pricingUtils.getPrice("Provider", "L");

        assertNotNull(price);
        assertEquals(5.0, price);
    }

    @Test
    void testGetPrice_NonExistingEntry() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);
        Double price = pricingUtils.getPrice("Provider", "S");

        assertNull(price);
    }

	@Test
	void testGetLowestPriceForSize() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("ProviderA", "L", 5.0);
        pricingUtils.addPrice("ProviderB", "L", 2.0);
        pricingUtils.addPrice("ProviderC", "S", 4.0);

        double lowestPrice = pricingUtils.getLowestPriceForSize("L");

        assertEquals(2.0, lowestPrice);
    }
	
	@Test
    void testGetLowestPriceForSize_NoMatchingSize() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("ProviderA", "L", 5.0);
        pricingUtils.addPrice("ProviderB", "L", 2.0);

        double lowestPrice = pricingUtils.getLowestPriceForSize("S");

        assertEquals(-1, lowestPrice);
    }

    @Test
    void testGetLowestPriceForSize_EmptyTable() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        double lowestPrice = pricingUtils.getLowestPriceForSize("L");

        assertEquals(-1, lowestPrice);
    }

    @Test
    void testProviderExists_True() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);

        assertTrue(pricingUtils.providerExists("Provider"));
    }

    @Test
    void testProviderExists_False() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("ProviderA", "L", 5.0);

        assertFalse(pricingUtils.providerExists("ProviderB"));
    }

    @Test
    void testSizeExists_True() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);

        assertTrue(pricingUtils.sizeExists("L"));
    }

    @Test
    void testSizeExists_False() {
        List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);

        pricingUtils.addPrice("Provider", "L", 5.0);

        assertFalse(pricingUtils.sizeExists("S"));
    }

}
