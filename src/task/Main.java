package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		 try (Scanner inputScanner = new Scanner(System.in)) {
	            System.out.println("Please provide the input file path: ");
	            String filePath = inputScanner.nextLine();

	            List<HashMap<String, Object>> priceTable = new ArrayList<>();
	            PricingUtils pricingUtils = new PricingUtils(priceTable);

	            pricingUtils.addPrice("LP", "S", 1.5);
	            pricingUtils.addPrice("LP", "M", 4.9);
	            pricingUtils.addPrice("LP", "L", 6.9);
	            pricingUtils.addPrice("MR", "S", 2.0);
	            pricingUtils.addPrice("MR", "M", 3.0);
	            pricingUtils.addPrice("MR", "L", 4.0);
	            
	            double budget = 10.0;

	            List<DiscountRule> discountRules = new ArrayList<>();
	            discountRules.add(new FreeLShippingRule());
	            discountRules.add(new LowestSPriceRule(pricingUtils));

	            FileProcessor fileProcessor = new FileProcessor();
	            TransactionParser transactionParser = new TransactionParser(pricingUtils);
	            DiscountCalculator discountCalculator = new DiscountCalculator(discountRules, budget);

	            DiscountHandler discountHandler = new DiscountHandler(fileProcessor, transactionParser, discountCalculator);
	            
	            discountHandler.processTransactionsFromFile(filePath);
	        }
	    }

}
