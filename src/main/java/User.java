import java.util.HashMap;

/**
 * Created by veronicacrowe on 5/22/16.
 */
public class User {

    private HashMap<String, HashMap<Long, BankAccount>> allUserBankAccounts = new HashMap<String, HashMap<Long, BankAccount>>();
    private HashMap<Long, BankAccount> userBankAccount = new HashMap<Long, BankAccount>();
    private int userPin;
    private String userName;

    public User(String userName, int userPin){
        this.userName = userName;
        this.userPin = userPin;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserPin() {
        return userPin;
    }

    public void addCheckingAccount(long accountNumber, OverdraftProtection overdraftProtection){
        userBankAccount.put(accountNumber, new CheckingAccount(accountNumber, overdraftProtection));
        allUserBankAccounts.put("Checking", userBankAccount);
    }

    public void addSavingsAccount(long accountNumber, OverdraftProtection overdraftProtection){
        userBankAccount.put(accountNumber, new SavingsAccount(accountNumber, overdraftProtection));
        allUserBankAccounts.put("Savings", userBankAccount);
    }

    public void addInvestmentAccount(long accountNumber, OverdraftProtection overdraftProtection){
        userBankAccount.put(accountNumber, new InvestmentAccount(accountNumber, overdraftProtection));
        allUserBankAccounts.put("Investment", userBankAccount);
    }

    public BankAccount getUserCheckingAccount(long accountNumber){
        return allUserBankAccounts.get("Checking").get(accountNumber);
    }

    public BankAccount getUserSavingsAccount(long accountNumber){
        return allUserBankAccounts.get("Savings").get(accountNumber);
    }

    public BankAccount getInvestmentAccount(long accountNumber){
        return allUserBankAccounts.get("Investment").get(accountNumber);
    }


}
