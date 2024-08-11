package task;

import java.time.Month;
import java.util.List;

public class DiscountCalculator {
    private final List < DiscountRule > discountRules;
    private double leftoverBudget;
    private int LPLCounter = 0;
    private Month lastProcessedMonth = null;

    public DiscountCalculator(List < DiscountRule > discountRules, double budget) {
        this.discountRules = discountRules;
        this.leftoverBudget = budget;
    }

    public double calculateDiscount(Transaction transaction) {
        Month currentMonth = transaction.getDate().getMonth();

        if (lastProcessedMonth != null && !lastProcessedMonth.equals(currentMonth)) {
            leftoverBudget = 10.0;
            LPLCounter = 0;
        }

        lastProcessedMonth = currentMonth;

        double discount = 0.0;

        if (transaction.getProvider().equals("LP") && transaction.getSize().equals("L")) {
            LPLCounter++;
        }

        for (DiscountRule rule: discountRules) {
            if (rule instanceof FreeLShippingRule) {
                discount += ((FreeLShippingRule) rule).applyDiscount(transaction, leftoverBudget, LPLCounter);
            } else {
                discount += rule.applyDiscount(transaction, leftoverBudget);
            }
        }

        leftoverBudget -= discount;
        return discount;
    }

}