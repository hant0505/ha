public class SavingsAccount extends Account {
    private static final double MAX_WITHDRAWAL = 1000.0;
    private static final double MIN_BALANCE = 5000.0;

    /**
     * g.
     *
     * @param accountNumber a
     * @param balance       b
     */
    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        try {
            doDepositing(amount);
            addTransaction(new Transaction(Transaction.TYPE_DEPOSIT_SAVINGS,
                    amount, balance - amount, balance));
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        try {
            if (amount > MAX_WITHDRAWAL) {
                throw new InvalidFundingAmountException("Số tiền rút không được vượt quá $1000.00");
            }
            // Kiểm tra số dư sau khi rút không dưới $5000
            if (balance - amount < MIN_BALANCE) {
                throw new BankException(String.format(
                        "Số dư không đủ sau khi rút: cần duy trì ít nhất $%.2f", MIN_BALANCE));
            }

            double initialBalance = getBalance();
            doWithdrawing(amount);
            addTransaction(new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, balance));
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }
}
