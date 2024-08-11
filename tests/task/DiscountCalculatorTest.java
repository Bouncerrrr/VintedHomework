package task;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiscountCalculatorTest {
	
	private DiscountCalculator discountCalculator;
    private FreeLShippingRule freeLShippingRule;
    private LowestSPriceRule lowestSPriceRule;
    private List<DiscountRule> discountRules;
    
    @BeforeEach
    public void setUp() {
    	
    	List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);
	        
	    pricingUtils.addPrice("LP", "S", 5.0);
	    pricingUtils.addPrice("LP", "L", 6.0);
	    pricingUtils.addPrice("A", "S", 3.0);
	    pricingUtils.addPrice("A", "L", 6.0);
	    lowestSPriceRule = new LowestSPriceRule(pricingUtils);
	    
    	List<DiscountRule> discountRules = new ArrayList<>();
        discountRules.add(new FreeLShippingRule());
        discountRules.add(new LowestSPriceRule(pricingUtils));
        discountCalculator = new DiscountCalculator(discountRules, 10.0);
    }
    
    @Test
    public void testCalculateDiscountWithLPLTransactionWithinSameMonthInBudget() {
       
        Transaction transaction1 = new Transaction(LocalDate.of(2015, Month.FEBRUARY, 01), "LP", "L", 6.0);
        Transaction transaction2 = new Transaction(LocalDate.of(2015, Month.FEBRUARY, 02), "LP", "L", 6.0);
        Transaction transaction3 = new Transaction(LocalDate.of(2015, Month.FEBRUARY, 03), "LP", "L", 6.0);

        double discount1 = discountCalculator.calculateDiscount(transaction1);
        double discount2 = discountCalculator.calculateDiscount(transaction2);
        double discount3 = discountCalculator.calculateDiscount(transaction3); 

        assertEquals(0.0, discount1, 0.001); 
        assertEquals(0.0, discount2, 0.001);  
        assertEquals(6.0, discount3, 0.001); 
       
    }
    
    @Test
    public void testCalculateDiscountWithLPLTransactionWithinSameMonthOutsideBudget() {
    	
    	Transaction[] transactions = new Transaction[]{
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 01), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 02), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 03), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 04), "LP", "L", 6.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 05), "LP", "L", 6.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 06), "LP", "L", 6.0)
    	    };

    	    double[] expectedDiscounts = {2.0, 2.0, 2.0, 0.0, 0.0, 4.0};

    	    for (int i = 0; i < transactions.length; i++) {
    	        double discount = discountCalculator.calculateDiscount(transactions[i]);
    	        assertEquals(expectedDiscounts[i], discount, 0.001, "Failed at transaction " + (i + 1));
    	    }
       
    }
    
    @Test
    public void testCalculateDiscountWithNonLPLTransactionInBudget() {
        
        Transaction transaction = new Transaction(LocalDate.of(2015, Month.FEBRUARY, 01), "LP", "S", 5.0);

        double discount = discountCalculator.calculateDiscount(transaction);

        assertEquals(2.0, discount, 0.001);
    }
    
    @Test
    public void testCalculateDiscountWithNonLPLTransactionOutsideBudget() {
        
    	Transaction[] transactions = new Transaction[]{
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 01), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 02), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 03), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 04), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 05), "LP", "S", 5.0),
    	        new Transaction(LocalDate.of(2015, Month.FEBRUARY, 06), "LP", "S", 5.0)
    	    };

    	    double[] expectedDiscounts = {2.0, 2.0, 2.0, 2.0, 2.0, 0.0};

    	    for (int i = 0; i < transactions.length; i++) {
    	        double discount = discountCalculator.calculateDiscount(transactions[i]);
    	        assertEquals(expectedDiscounts[i], discount, 0.001, "Failed at transaction " + (i + 1));
    	    }
    }
    
    @Test
    public void testCalculateDiscountMounthReset() {
        
        Transaction transaction1 = new Transaction(LocalDate.of(2015, Month.FEBRUARY, 01), "LP", "S", 5.0);
        Transaction transaction2 = new Transaction(LocalDate.of(2015, Month.MARCH, 01), "LP", "S", 5.0);

        double discount1 = discountCalculator.calculateDiscount(transaction1);
        double discount2 = discountCalculator.calculateDiscount(transaction2);

        assertEquals(2.0, discount1, 0.001);
        assertEquals(2.0, discount2, 0.001);
    }

}
