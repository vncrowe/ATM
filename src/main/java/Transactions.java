import java.util.Date;

/**
 * Created by veronicacrowe on 5/18/16.
 */
public class Transactions {

    private double amount;
    private double destinationAccount;
    private double financialTransactionNumber;
    private Date timestamp;
    private String transactionsType;
    private double sourceAccount;
    private double sourceAccountCash;

    public Transactions(String transactionsType,double amount, Date date){
        this.transactionsType = transactionsType;
        this.amount = amount;
        this.timestamp = new Date();


    }

    @Override
    public String toString() {
        return "Transactions:\n" +
                "amount= " + amount +
                "\ntransactionsType= " + transactionsType +
                "\ntimestamp= " + timestamp;
    }
}
                              