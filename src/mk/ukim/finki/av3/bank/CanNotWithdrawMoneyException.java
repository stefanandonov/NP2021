package mk.ukim.finki.av3.bank;

public class CanNotWithdrawMoneyException extends Exception {

    /**
     * You can pass all types of arguments and then create a string to pass to the super constructor.
     * In this case we pass the current amount and the withdrawal amount.
     * @param currentAmount
     * @param amount
     */
    public CanNotWithdrawMoneyException(double currentAmount, double amount) {
        super(String.format("Your current amount is: %.2f, and you can not withdraw this amount: %.2f.",
                currentAmount, amount));
    }
}
