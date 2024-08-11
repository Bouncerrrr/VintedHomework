package task;

public interface DiscountRule {
	double applyDiscount(Transaction transaction, double leftoverBudget);
}
