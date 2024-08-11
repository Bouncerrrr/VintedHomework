package task;

import java.io.BufferedReader;
import java.io.IOException;

public class DiscountHandler {

    private final FileProcessor fileProcessor;
    private final TransactionParser transactionParser;
    private final DiscountCalculator discountCalculator;

    public DiscountHandler(FileProcessor fileProcessor,
    					   TransactionParser transactionParser,
    					   DiscountCalculator discountCalculator) {
    	
        this.fileProcessor = fileProcessor;
        this.transactionParser = transactionParser;
        this.discountCalculator = discountCalculator;
    }

    public void processTransactionsFromFile(String filePath) {
        fileProcessor.processFile(filePath, this::processLine);
    }
    
    public String fileToString(BufferedReader input) {
        StringBuilder content = new StringBuilder();
        String line;
        try {
            while ((line = input.readLine()) != null) {
                content.append(line).append("\n");
                processLine(line);  // Process each line as it is read
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void processLine(String line) {
        Transaction transaction = transactionParser.parseLine(line);
        if (transaction != null) {
            processTransaction(line, transaction);
        } else {
            System.out.println(line + " ignored");
        }
    }

    private void processTransaction(String line, Transaction transaction) {
        double discount = discountCalculator.calculateDiscount(transaction);
        double finalPrice = transaction.getOriginalPrice() - discount;
        String discountString = (discount == 0.0) ? "-" : String.format("%.2f", discount);
        System.out.printf(line + " %.2f %s\n", finalPrice, discountString);
    }
    
}