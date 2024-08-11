package task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TransactionParserTest {
    
	 private TransactionParser transactionParser;
	 
	 @BeforeEach
	 public void setUp() {
		 
		 List<HashMap<String, Object>> priceTable = new ArrayList<>();
         PricingUtils pricingUtils = new PricingUtils(priceTable);
	        
	     pricingUtils.addPrice("ProviderA", "S", 2.0);
	     pricingUtils.addPrice("ProviderA", "L", 5.0);
	     pricingUtils.addPrice("ProviderB", "S", 1.0);
	     pricingUtils.addPrice("ProviderB", "L", 6.0);
	     transactionParser = new TransactionParser(pricingUtils);
	 }
	 
	 @Test
	    public void testParseLineInvalidPartsLength() {

	        String line = "2015-02-01 S";
	        Transaction result = transactionParser.parseLine(line);

	        assertNull(result);
	    }
	 
	 @Test
	    public void testParseLineInvalidDate() {
	       
	        String line = "invalid-date S ProviderA";
	        Transaction result = transactionParser.parseLine(line);

	        assertNull(result);
	    }
	 
	 @Test
	    public void testParseLineInvalidProvider() {
		 
	        String line = "2015-02-01 S ProviderWrong";

	        Transaction result = transactionParser.parseLine(line);

	        assertNull(result);
	    }
	 
	 @Test
	    public void testParseLineInvalidSize() {
		 
	        String line = "2015-02-01 Wrong ProviderA";

	        Transaction result = transactionParser.parseLine(line);

	        assertNull(result);
	    }

	 @Test
	    public void testParseLineValidInput() {
		 	String line = "2015-02-01 S ProviderA";
	        LocalDate expectedDate = LocalDate.parse("2015-02-01");
	        String expectedSize = "S";
	        String expectedProvider = "ProviderA";
	        double expectedPrice = 2.0;

	        Transaction result = transactionParser.parseLine(line);

	        assertNotNull(result);
	        assertEquals(expectedDate, result.getDate());
	        assertEquals(expectedSize, result.getSize());
	        assertEquals(expectedProvider, result.getProvider());
	        assertEquals(expectedPrice, result.getOriginalPrice());
	    }



}
