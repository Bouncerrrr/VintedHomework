package task;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

class DiscountHandlerTest {
	
	private FileProcessor fileProcessor;
    private TransactionParser transactionParser;
    private DiscountCalculator discountCalculator;
    private DiscountHandler discountHandler;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private List<String> outputLines;

    @BeforeEach
    void setUp() {
    	List<HashMap<String, Object>> priceTable = new ArrayList<>();
        PricingUtils pricingUtils = new PricingUtils(priceTable);
	        
	    pricingUtils.addPrice("ProviderA", "S", 5.0);
	    pricingUtils.addPrice("ProviderA", "L", 6.0);
	    pricingUtils.addPrice("ProviderB", "S", 3.0);
	    pricingUtils.addPrice("ProviderB", "L", 6.0);
	    
        fileProcessor = new FileProcessor();
        transactionParser = new TransactionParser(pricingUtils);
        
        List<DiscountRule> discountRules = new ArrayList<>();
        discountRules.add(new FreeLShippingRule());
        discountRules.add(new LowestSPriceRule(pricingUtils));
        
        discountCalculator = new DiscountCalculator(discountRules, 10.0);
        discountHandler = new DiscountHandler(fileProcessor, transactionParser, discountCalculator);
        outputLines = new ArrayList<>();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testProcessTransactionsValidLine() {
    	
    	String fileContent = "2015-02-01 S ProviderA\n";
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));

        discountHandler.fileToString(reader);

        System.out.flush();

        String expectedOutput = "2015-02-01 S ProviderA 3.00 2.00";
        assertEquals(expectedOutput, outContent.toString().trim());
    	
    }
    
    @Test
    void testProcessTransactionsInvalidLine() {
    	
    	String fileContent = "2015-02-01 S Wrong\n";
        BufferedReader reader = new BufferedReader(new StringReader(fileContent));

        discountHandler.fileToString(reader);

        System.out.flush();

        String expectedOutput = "2015-02-01 S Wrong ignored";
        assertEquals(expectedOutput, outContent.toString().trim());
    	
    }

    
}
