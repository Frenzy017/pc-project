
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<String, String> accounts = new HashMap<>();

    public Map<String, String> getAccounts() {
        return accounts;
    }

    public void add(Account account) {
        String password = account.getPassword();
        String username = account.getUsername();

        if (!accounts.containsKey(username) || !accounts.containsValue(password)) {
            accounts.put(username, password);
        }
    }
}
