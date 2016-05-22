import java.util.HashMap;

/**
 * Created by veronicacrowe on 5/22/16.
 */
public class UserHandler {
    static HashMap<Integer, User> bank = new HashMap<Integer, User>();
    public static void addAccount(int IdentificationID, User user){
        bank.put(IdentificationID, user);
    }

}
