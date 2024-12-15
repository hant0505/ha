import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Account {
    public static final String CHECKING = "CHECKING";
    public static final String SAVINGS = "SAVINGS";
    protected long accountNumber;
    protected double balance;
    protected List<Transaction> transactionList;

    /**
     * he.
     */
    public Account() {
        this.accountNumber = 0;
        this.balance = 0;
        this.transactionList = new ArrayList<>();
    }

    /**
     * a.
     *
     * @param accountNumber a
     * @param balance       b
     */
    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionList = new ArrayList<Transaction>();
    }

    public abstract void withdraw(double amount);

    public abstract void deposit(double amount);

    /**
     * do.
     *
     * @param amount a
     * @throws BankException bank
     */
    public void doWithdrawing(double amount) throws BankException {
        if (amount < 0) {
            throw new InvalidFundingAmountException(String.format(
                    "Số tiền không hợp lệ: $%.2f", amount));
        }

        if (amount > balance) {
            throw new InsufficientFundsException(String.format(
                    "Số dư tài khoản không đủ $%.2f để thực hiện giao dịch", amount));
        }
        balance -= amount;
    }

    /**
     * depo.
     *
     * @param amount am
     * @throws BankException bank
     */
    public void doDepositing(double amount) throws BankException {
        if (amount < 0) {
            throw new InvalidFundingAmountException(String.format(
                    "Số tiền không hợp lệ: $%.2f", amount));
        }
        balance += amount;
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    /**
     * trans.
     *
     * @return n
     */
    public String getTransactionHistory() {
        StringBuilder sb = new StringBuilder(
                "Lịch sử giao dịch của tài khoản " + accountNumber + ":\n");
        for (Transaction transaction : transactionList) {
            sb.append(transaction.getTransactionSummary());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account other = (Account) o;
        return accountNumber == other.accountNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}

