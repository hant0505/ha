public class CheckingAccount extends Account {
    public CheckingAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void withdraw(double amount) {
        try {
            double initialBalance = balance;
            doWithdrawing(amount);
            addTransaction(new Transaction(
                    Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, balance));
        } catch (BankException e) {
            System.out.println("Lỗi rút tiền: " + e.getMessage());
        }
    }

    @Override
    public void deposit(double amount) {
        try {
            doDepositing(amount);
            addTransaction(new Transaction(Transaction.TYPE_DEPOSIT_CHECKING,
                    amount, balance - amount, balance));
        } catch (BankException e) {
            System.out.println("Lỗi nạp tiền: " + e.getMessage());
        }
    }
}
