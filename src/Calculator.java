import javax.swing.*;
import java.util.Map;

public class Calculator {
    private static final double WEEKS_TO_MONTH_FACTOR = 4.5;
    private static final int MONTHS_TO_QUARTER_FACTOR = 3;
    private final Map<Prompts, JTextField> fieldByName;
    public double bills, paidCancelsFactor, cancelsFactor, curWage, wantedWage;
    public int curClientAmount, wantedClientAmount;
    public final double curSalary, betterWageSalary, moreClientsSalary, maxSalary;

    public Calculator(Map<Prompts, JTextField> fieldByName) {
        this.fieldByName = fieldByName;
        setVariables();
        setBills();
        setCancelsPercentage();
        curSalary = calcSalary(curWage, curClientAmount);
        betterWageSalary = calcSalary(wantedWage, curClientAmount);
        moreClientsSalary = calcSalary(curWage, wantedClientAmount);
        maxSalary = calcSalary(wantedWage, wantedClientAmount);
    }

    private double getDoubleValue(Prompts prompt) {
        var field = fieldByName.get(prompt);
        var text = field.getText();
        return Double.parseDouble(text);
    }

    private int getIntValue(Prompts prompt) {
        var field = fieldByName.get(prompt);
        var text = field.getText();
        return Integer.parseInt(text);
    }

    private void setVariables() {
        curWage = getDoubleValue(Prompts.CUR_WAGE);
        wantedWage = getDoubleValue(Prompts.WANTED_WAGE);
        curClientAmount = getIntValue(Prompts.CUR_NUM_OF_CLIENTS);
        wantedClientAmount = getIntValue(Prompts.WANTED_NUM_OF_CLIENTS);
    }

    private void setBills() {
        Prompts[] billsPrompts = {Prompts.ELECTRICITY, Prompts.WATER, Prompts.INTERNET, Prompts.RATES};
        double sum = 0;
        for (var prompt : billsPrompts) {
            sum += getDoubleValue(prompt);
        }
        bills = sum / 4 * MONTHS_TO_QUARTER_FACTOR;
    }

    public void setCancelsPercentage() {

        double treatmentsAmount = curClientAmount * WEEKS_TO_MONTH_FACTOR;
        var paidCancelsAmount = getDoubleValue(Prompts.PAID_CANCELS_AMOUNT);
        var cancelsAmount =  getDoubleValue(Prompts.SELF_CANCELS_AMOUNT);
        paidCancelsFactor = paidCancelsAmount / treatmentsAmount;
        cancelsFactor = cancelsAmount / treatmentsAmount;
    }

    public double calcSalary(double wage, int costumerAmount) {
        var taxFactor = getDoubleValue(Prompts.TAX_FACTOR);

        double income = getIncome(wage, costumerAmount);
        double incomeAfterTaxes = income - income * (taxFactor / 100);

        double expenses = getExpenses(costumerAmount);

        double quarterSalary = incomeAfterTaxes - expenses;
        return quarterSalary / MONTHS_TO_QUARTER_FACTOR;
    }

    private double getIncome(double wage, int costumerAmount) {
        var cancelingFeeFactor = getDoubleValue(Prompts.CANCELING_FEE_FACTOR);
        double expectedWeeklyIncome = wage * costumerAmount;
        double brutoIncome = expectedWeeklyIncome * WEEKS_TO_MONTH_FACTOR * MONTHS_TO_QUARTER_FACTOR;

        double treatmentsAmount = costumerAmount * WEEKS_TO_MONTH_FACTOR;
        double paidCancelsAmount = paidCancelsFactor * treatmentsAmount;
        double cancelsAmount = cancelsFactor * treatmentsAmount;
        double cancelsCost = wage * ( (1 - cancelingFeeFactor) * paidCancelsAmount + cancelsAmount );

        return brutoIncome - cancelsCost;
    }

    private double getExpenses(int costumerAmount) {
        var products = getDoubleValue(Prompts.PRODUCTS);
        double productsPerCostumer = products / curClientAmount;
        var totalProducts = productsPerCostumer * costumerAmount;

        var rent = getDoubleValue(Prompts.RENT);
        var education = getDoubleValue(Prompts.EDUCATION);
        var advision = getDoubleValue(Prompts.ADVISION);
        var retirementFee = getDoubleValue(Prompts.RETIREMENT_FEE);

        return bills + totalProducts + rent + education + advision + retirementFee;
    }
}
