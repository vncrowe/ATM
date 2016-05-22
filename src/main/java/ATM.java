import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Created by veronicacrowe on 5/22/16.
 */
public class ATM {

    Scanner sc = new Scanner(System.in);
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private boolean powerOn = true;
    private BankAccount onDisplay;
    private int currentIDonDisplay;

    public void menuPrompt() {
        System.out.println("Welcome to the Bank, please select an action:" +
                "1. Withdrawal\n" +
                "2. Deposit\n" +
                "3. Transfer\n" +
                "4. Get Account Balance\n" +
                "5. Open New Account\n" +
                "6. Close Bank Account\n" +
                "7. Print Transaction History");
    }

    public void userPreference() {
        System.out.println("Hello, are you a:\n" +
                "1. New user\n" +
                "2. Returning user");
    }

    public void handlerNewReturningUser(int userPreference) {
        switch (userPreference) {
            case 1:
                System.out.println("Please enter your name.");
                sc.next();
                String name = sc.nextLine();
                currentIDonDisplay = UserHandler.bank.size() + 1;
                System.out.println("Please enter a four number pin code.");
                int pin = sc.nextInt();
                UserHandler.addAccount(currentIDonDisplay, new User(name, pin));
                System.out.println("Hello " + name + ". Your  ID is " + currentIDonDisplay + ". Your pin number is " + pin + ".");
                openNewBankAccount(currentIDonDisplay);
                break;
            case 2:
                System.out.println("Welcome back! Please enter your ID.");
                currentIDonDisplay = sc.nextInt();
                System.out.println("Please enter your pin code.");
                int pinCodeEnter = sc.nextInt();
                int actualPinCode = UserHandler.bank.get(currentIDonDisplay).getUserPin();
                if (actualPinCode == pinCodeEnter) {
                    System.out.println("What kind of account would you like to access?\n" +
                            "1. Checking\n" +
                            "2. Saving\n" +
                            "3. Investment");
                    int userSelection = sc.nextInt();
                    System.out.println("Please enter the account number you'd like to access?");
                    long accountNumberSelection = sc.nextLong();
                    onDisplay = searchForBankAccount(userSelection, currentIDonDisplay, accountNumberSelection);
                } else {
                    System.out.println("Sorry, you entered the wrong pin.");
                    runATM();
                }
            default:
                System.out.println("Please enter 1 for new user or 2 for returning user.");
        }

    }

    public void selectionFromMenuPrompt(int commandEnter) {
        switch (commandEnter) {
            case 1:
                debitMessage();
                onDisplay.debit(getAmountFromUser());
                break;
            case 2:
                creditMessage();
                onDisplay.credit(getAmountFromUser());
                break;
            case 3:
                transferMessage();
                int userAccountType = getCommandFromUser();
                transferIDmessage();
                int desireIDnumber = askForIDnumber();
                askForAccountNumberMessage();
                long userAccountNumber = askForAccountNumber();
                askForAmountMessage();
                double userAmountToTransfer = askForAmount();
                BankAccount transferToBankAccount = searchForBankAccount(userAccountType, desireIDnumber, userAccountNumber);
                onDisplay.makeTransfer(transferToBankAccount, userAmountToTransfer);
                break;
            case 4:
                onDisplay.getAccountBalance();
                break;
            case 5:
                openNewBankAccount(currentIDonDisplay);
                break;
            case 6:
                onDisplay.setStatus(AccountStatus.CLOSED);
                break;
            case 7:
                onDisplay.printTransaction();
                break;
            default:
                System.out.println("Selection unknown. Please enter a command value 1-7.");
        }
    }

    public double getAmountFromUser() {
        return sc.nextDouble();
    }

    public void debitMessage() {
        System.out.println("Please enter the amount you'd like to debit.");
    }

    public void creditMessage() {
        System.out.println("Please enter the amount you'd like to credit.");
    }

    public void transferMessage() {
        System.out.println("Please enter the type of account you'd like to transfer to:\n1. Checking\n2. Savings\n3.Investment");
        int accountTypeFromUser = getCommandFromUser();

    }

    public int getCommandFromUser() {
        return sc.nextInt();
    }

    public int askForIDnumber() {
        return sc.nextInt();
    }

    public void transferIDmessage() {
        System.out.println("Please enter the ID number for the account you'd like to transfer to.");
    }

    public void askForAccountNumberMessage() {
        System.out.println("Please enter the account number you'd like to transfer to.");
    }

    public long askForAccountNumber() {
        return sc.nextLong();
    }

    public void askForAmountMessage() {
        System.out.println("Please enter the amount you'd like to transfer.");
    }

    public double askForAmount() {
        return sc.nextDouble();
    }

    public BankAccount searchForBankAccount(int userAccountType, int desireIDnumber, long userAccountNumber) {
        BankAccount bankAccount = null;
        switch (userAccountType) {
            case 1:
                bankAccount = UserHandler.bank.get(desireIDnumber).getUserCheckingAccount(userAccountNumber);
                break;
            case 2:
                bankAccount = UserHandler.bank.get(desireIDnumber).getUserSavingsAccount(userAccountNumber);
                break;
            case 3:
                bankAccount = UserHandler.bank.get(desireIDnumber).getInvestmentAccount(userAccountNumber);
                break;
        }
        return bankAccount;
    }

    public void openNewBankAccount(int id) {
        System.out.println("Would you like to open a:\n" +
                "1. Checking\n" +
                "2. Saving \n" +
                "3. Investment");
        int userSelection = sc.nextInt();
        System.out.println("What would you like for your account number to be?");
        long userSelectionAccountNumber = sc.nextLong();
        switch (userSelection) {
            case 1:
                UserHandler.bank.get(id).addCheckingAccount(userSelectionAccountNumber, OverdraftProtection.ENABLED);
                onDisplay = searchForBankAccount(1, id, userSelectionAccountNumber);
                break;
            case 2:
                UserHandler.bank.get(id).addSavingsAccount(userSelectionAccountNumber, OverdraftProtection.ENABLED);
                onDisplay = searchForBankAccount(2, id, userSelectionAccountNumber);
                break;
            case 3:
                UserHandler.bank.get(id).addInvestmentAccount(userSelectionAccountNumber, OverdraftProtection.ENABLED);
                onDisplay = searchForBankAccount(3, id, userSelectionAccountNumber);
                break;
        }

    }

    public void runATM(){
        userPreference();
        handlerNewReturningUser(sc.nextInt());
        while(powerOn){
            startATM();

        }
    }

    public void startATM(){
        menuPrompt();
        int commandEntered = sc.nextInt();
        selectionFromMenuPrompt(commandEntered);
    }
}


