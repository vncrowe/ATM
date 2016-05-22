import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by veronicacrowe on 5/18/16.
 */
public class BankAccountTest {
    private BankAccount b1;
    private BankAccount b2;
    private BankAccount b3;
    double delta = 10e-15;

    @Before
    public void initialize(){
        b1 = new CheckingAccount(10000, OverdraftProtection.ENABLED);
        b2 = new SavingsAccount(10001, OverdraftProtection.DISABLED);
        b3 = new InvestmentAccount(10003, OverdraftProtection.ENABLED);
    }
    @Test
    public void setAccountNameTest(){
        b2.setAccountHolderName("Veronica");
        String expectedName = "Veronica";
        String actualName = b2.getAccountHolderName();
        assertEquals(expectedName,actualName);
    }
    @Test
    public void setStatusTest(){
        b1.setStatus(AccountStatus.CLOSED);
        AccountStatus expectedStatus = AccountStatus.OPEN;
        AccountStatus actualStatus = b1.getStatus();
        assertEquals(expectedStatus,actualStatus);
        b1.deductDebit(10000);
        b1.setStatus(AccountStatus.CLOSED);
        expectedStatus = AccountStatus.CLOSED;
        actualStatus = b1.getStatus();
        assertEquals(expectedStatus,actualStatus);
    }
    @Test
    public void getAccountBalanceFreezeTest(){
        b1.setStatus(AccountStatus.FREEZE);
        double expectedValue = 0;
        double actualValue = b1.getAccountBalance();
        assertEquals(expectedValue,actualValue,delta);
    }
    @Test
    public void deductDebitTest(){
        b1.deductDebit(1000);
        double expectedValue = 9000;
        double actualValue = b1.getAccountBalance();
        assertEquals(expectedValue,actualValue,delta);
    }
    @Test
    public void addCreditTest(){
        b1.addCredit(1000);
        double expectedValue = 11000;
        double actualValue = b1.getAccountBalance();
        assertEquals(expectedValue,actualValue,delta);
    }/*
    @Test
    public void debitTest(){
        String expectedString = "Debit approved!";
        String actualString = b1.debit(10000);
        assertEquals(expectedString,actualString);
        expectedString = "Debit not approved";
        actualString = b1.debit(10000);
        assertEquals(expectedString,actualString);
        b1.setOverdraftProtection(OverdraftProtection.DISABLED);
        expectedString = "Debit approved!";
        actualString = b1.debit(10000);
        assertEquals(expectedString,actualString);
    }*/
    @Test
    public void creditTest(){
        String expectedString = "Credit approved!";
        String actualString = b1.credit(10000);
        assertEquals(expectedString,actualString);
        b1.setStatus(AccountStatus.FREEZE);
        expectedString = "Credit not approved";
        actualString = b1.credit(10000);
        assertEquals(expectedString,actualString);
    }
    @Test
    public void transferTest(){
        String expectedString = "Transfer approved!";
        String actualString = b1.transfer(b2, 10000);
        assertEquals(expectedString,actualString);
        expectedString = "Transfer not approved";
        actualString = b1.transfer(b2, 10000);
        assertEquals(expectedString,actualString);
        b2.setStatus(AccountStatus.FREEZE);
        expectedString = "Transfer not approved";
        actualString = b2.transfer(b1,100);
        assertEquals(expectedString,actualString);
    }

}
