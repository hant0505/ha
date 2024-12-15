import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Bank {
    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>(); // Khởi tạo danh sách khách hàng
    }

    /**
     * s.
     *
     * @param inputStream in
     */
    public void readCustomerList(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            Customer currentCustomer = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println("Reading line: " + line);
                String[] customerInfo = line.split("\\s+");
                //System.out.println(Arrays.toString(customerInfo));

                if (customerInfo.length >= 2
                        && customerInfo[customerInfo.length - 1].matches("\\d{9}")) {
                    long idNumber = Long.parseLong(customerInfo[customerInfo.length - 1]);
                    String fullName = String.join(" ",
                            Arrays.copyOfRange(customerInfo, 0, customerInfo.length - 1));

                    currentCustomer = new Customer();
                    currentCustomer.setFullName(fullName);
                    currentCustomer.setIdNumber(idNumber);
                    customerList.add(currentCustomer);
                    //System.out.println("Added customer: " + fullName);
                } else if (customerInfo.length == 3 && currentCustomer != null) {
                    String accountNumberStr = customerInfo[0];
                    if (accountNumberStr.matches("\\d{10}")) {
                        long accountNumber = Long.parseLong(accountNumberStr);
                        String accountType = customerInfo[1];
                        double balance = Double.parseDouble(customerInfo[2]);

                        Account account = null;
                        if (accountType.equals(Account.CHECKING)) {
                            account = new CheckingAccount(accountNumber, balance);
                        } else if (accountType.equals(Account.SAVINGS)) {
                            account = new SavingsAccount(accountNumber, balance);
                        }

                        if (account != null) {
                            currentCustomer.addAccount(account);
                        }
                    }
                } else {
                    System.out.println("Dòng dữ liệu không hợp lệ: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc danh sách khách hàng: " + e.getMessage());
        }
    }

    /**
     * ha.
     *
     * @return s
     */
    public String getCustomersInfoByIdOrder() {
        Collections.sort(customerList, Comparator.comparingLong(Customer::getIdNumber));
        StringBuilder s = new StringBuilder();
        for (Customer customer : customerList) {
            s.append(customer.getCustomerInfo());
        }
        return s.toString();
    }

    /**
     * ha.
     *
     * @return s
     */
    public String getCustomersInfoByNameOrder() {
        Collections.sort(customerList, Comparator.comparing(Customer::getFullName));
        StringBuilder s = new StringBuilder();
        for (Customer customer : customerList) {
            s.append(customer.getCustomerInfo());
        }
        return s.toString();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}

