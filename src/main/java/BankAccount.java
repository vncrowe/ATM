import java.util.ArrayList;
import java.util.Date;

/**
 * Created by veronicacrowe on 5/18/16.
 */
public class BankAccount {


    private long accountNumber;
    private AccountType accountType;
    private double accountBalance;
    private String accountHolderName;
    private double interestRate;
    private AccountStatus accountStatus;
    private OverdraftProtection overdraftProtection;
    public ArrayList<Transactions> transaction= new ArrayList<Transactions>();




    public BankAccount(long accountNumber, OverdraftProtection overdraftProtection){
        this.accountNumber = accountNumber;
        accountBalance = 0;
        accountStatus = AccountStatus.OPEN;
        if (accountType == AccountType.SAVINGS || accountType == AccountType.INVESTMENT){
            interestRate = 4;
        }
        this.overdraftProtection = overdraftProtection;
    }

    //set name for bank account holder
    public void setAccountHolderName(String name) {
        if (accountStatus != AccountStatus.CLOSED) {
            this.accountHolderName = name;
        }
    }

     public String getAccountHolderName(){
        return accountHolderName;
    }



    //set account status
    public void setStatus(AccountStatus newAccountStatus) {
        if (accountStatus != AccountStatus.CLOSED) {
            accountStatus = newAccountStatus;
        }
        if(newAccountStatus == AccountStatus.CLOSED){
            if(accountBalance == 0){
                accountStatus = newAccountStatus;
            }
        }
    }

    public AccountStatus getStatus(){
        return accountStatus;
    }


    //get account balance
    public double getAccountBalance(){
        if (accountStatus == AccountStatus.FREEZE){
            return 0;
        }
        return accountBalance;
    }

    public void setAccountBalance(double amount){
        accountBalance = amount;
    }


    //deduct debit from account balance
    public void deductDebit(double debit){
        accountBalance = accountBalance - debit;
        addTransactions("debit", debit, new Date());

    }

    //add credit from account balance
    public void addCredit(double credit){

        accountBalance = accountBalance + credit;
        addTransactions("credit", credit, new Date());

    }

    public String debit(double debit){
        if(accountStatus == AccountStatus.OPEN){
            if (overdraftProtection == OverdraftProtection.ENABLED){
                if(accountBalance > debit){
                    deductDebit(debit);
                    return "Debit approved!";
                }
            }
            if (overdraftProtection == OverdraftProtection.DISABLED){
                deductDebit(debit);
                return "Debit approved!";
            }
        }
        return "Debit not approved";
    }

    public String credit(double credit){
        if (accountStatus == AccountStatus.OPEN){
            addCredit(credit);
            return "Credit approved!";
        }
        return "Credit not approved";
    }

    public void makeTransfer(BankAccount transferToAccount, double transferValue){
        deductDebit(transferValue);
        transferToAccount.addCredit(transferValue);
        addTransactions("transfer", transferValue, new Date());
    }

    public String transfer(BankAccount transferToAccount, double transferValue){
        if(accountStatus == AccountStatus.OPEN && transferToAccount.getStatus() == AccountStatus.OPEN){
            if (accountBalance - transferValue >= 0){
                makeTransfer(transferToAccount, transferValue);
                return "Transfer approved";
            }
        }
        return "Transfer not approved";
    }

    public void addTransactions(String transactionsType,double amount, Date date){
        transaction.add(new Transactions(transactionsType, amount, date));
    }

    public void printTransaction(){
        for(int i=0; i< transaction.size(); i++){
            System.out.println(transaction.get(i).toString());
        }
    }




}
