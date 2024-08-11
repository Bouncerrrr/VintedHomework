package task;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FreeLShippingRuleTest {
	
	private FreeLShippingRule freeLShippingRule;

	@BeforeEach
    public void setUp() {
		
        freeLShippingRule = new FreeLShippingRule();
    }

    @Test
    public void testApplyDiscountWithoutLPLCounter() {
       
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "L", 15.0);
        double leftoverBudget = 10.0;

        double discount = freeLShippingRule.applyDiscount(transaction, leftoverBudget);

        assertEquals(0.0, discount, 0.001);
    }
    
    @Test
    public void testApplyDiscountWithLPLCounterNotReached() {
        
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "L", 11.0);
        double leftoverBudget = 10.0;
        int LPLCounter = 2; 

        double discount = freeLShippingRule.applyDiscount(transaction, leftoverBudget, LPLCounter);

        assertEquals(0.0, discount, 0.001);
    }
    
    @Test
    public void testApplyDiscountWithLPLCounterReachedWithinBudget() {
       
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "L", 6.0);
        double leftoverBudget = 10.0; 
        int LPLCounter = 3; 

        double discount = freeLShippingRule.applyDiscount(transaction, leftoverBudget, LPLCounter);

        assertEquals(6.0, discount, 0.001); 
    }

    @Test
    public void testApplyDiscountWithLPLCounterReachedExceedingBudget() {
      
        Transaction transaction = new Transaction(LocalDate.now(), "ProviderA", "L", 11.0);
        double leftoverBudget = 10.0; 
        int LPLCounter = 3;

        double discount = freeLShippingRule.applyDiscount(transaction, leftoverBudget, LPLCounter);

        assertEquals(10.0, discount, 0.001);
    }

}
