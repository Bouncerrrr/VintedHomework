package task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LowestSPriceRuleTest {
	
	private LowestSPriceRule lowestSPriceRule;
	
	@BeforeEach
	 public void setUp() {
		
		List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);
	        
	    pricingUtils.addPrice("ProviderA", "S", 5.0);
	    pricingUtils.addPrice("ProviderA", "L", 6.0);
	    pricingUtils.addPrice("ProviderB", "S", 3.0);
	    pricingUtils.addPrice("ProviderB", "L", 6.0);
	    lowestSPriceRule = new LowestSPriceRule(pricingUtils);
	 }

	@Test
    public void testApplyDiscountForSizeSWithinBudget() {

        Transaction transaction = new Transaction(LocalDate.now() , "ProviderA", "S", 5.0);

        double leftoverBudget = 5.0;

        double discount = lowestSPriceRule.applyDiscount(transaction, leftoverBudget);

        assertEquals(2.0, discount, 0.001); 
    }
	
	@Test
    public void testApplyDiscountForSizeSExceedingBudget() {
        
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "S", 5.0);

        double leftoverBudget = 1.0;

        double discount = lowestSPriceRule.applyDiscount(transaction, leftoverBudget);

        assertEquals(1.0, discount, 0.001);
    }
	
	@Test
    public void testApplyDiscountForSizeOtherThanS() {
       
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "L", 6.0);
        
        double leftoverBudget = 5.0;

        double discount = lowestSPriceRule.applyDiscount(transaction, leftoverBudget);

        assertEquals(0.0, discount, 0.001); 
    }


}
