package mk.ukim.finki.av3.bank;

public abstract class Account {

    private String accountOwner;
    private int id;
    private static int idSeed = 10000;
    private double currentAmount;

    /**
     * AccountType can be used to avoid the usage of instanceof
     */
    private AccountType accountType;

    public Account(String accountOwner, double currentAmount) {
        this.accountOwner = accountOwner;
        this.currentAmount = currentAmount;
        this.id = idSeed;
        idSeed++;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount) {
        currentAmount += amount;
    }

    public void withdrawAmount(double amount) throws CanNotWithdrawMoneyException {
        if (currentAmount < amount)
            throw new CanNotWithdrawMoneyException(currentAmount, amount);
        currentAmount -= amount;
    }

    public abstract AccountType getAccountType();

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}
