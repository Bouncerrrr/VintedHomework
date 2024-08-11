package task;

public class LowestSPriceRule implements DiscountRule {

    private final PricingUtils pricingUtils;

    public LowestSPriceRule(PricingUtils pricingUtils) {
        this.pricingUtils = pricingUtils;
    }

    @Override
    public double applyDiscount(Transaction transaction, double leftoverBudget) {
    	String size = transaction.getSize();
        if (size.equals("S")) {
            double lowestSPrice = pricingUtils.getLowestPriceForSize(size);
            return Math.min(transaction.getOriginalPrice() - lowestSPrice, leftoverBudget);
        }
        return 0.0;
    }
}
