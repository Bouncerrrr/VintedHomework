package task;

public class FreeLShippingRule implements DiscountRule {

	@Override
    public double applyDiscount(Transaction transaction, double leftoverBudget) {
        
        return 0.0;
    }

    public double applyDiscount(Transaction transaction, double leftoverBudget, int LPLCounter) {
        if (LPLCounter == 3) {
            return Math.min(transaction.getOriginalPrice(), leftoverBudget);
        }
        return 0.0;
    }
}
