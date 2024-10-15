package bank.database;

public class DBQueries {
    public static final String LOGIN_QUERY = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
    public static final String GET_CLIENT_ACCOUNTS_QUERY = """
            SELECT accounts.*, users.first_name, users.last_name
            FROM accounts
            JOIN users ON accounts.client_id = users.id
            WHERE client_id = ? 
            """;
    public static final String DEPOSIT_QUERY = "UPDATE accounts SET balance = balance + ? WHERE rib = ?";
    public static final String WITHDRAW_QUERY = "UPDATE accounts SET balance = balance - ? WHERE rib = ?";
    public static final String GET_ACCOUNTS_QUERY = """
            SELECT accounts.*, users.first_name, users.last_name
            FROM accounts
            JOIN users ON accounts.client_id = users.id
            """;
    public static final String SEARCH_ACCOUNT_QUERY = """
            SELECT accounts.*, users.first_name, users.last_name
            FROM accounts
            JOIN users ON accounts.client_id = users.id
            WHERE accounts.rib::text like ? 
            """;
    public static final String GET_CLIENTS_QUERY = "SELECT * FROM users WHERE role = 'CLIENT'";
    public static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ? WHERE id = ?";

    public static final String GET_USER_QUERY = "SELECT * FROM users WHERE id = ?";

    public static final String ADD_CLIENT_QUERY= "INSERT INTO users (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, 'CLIENT')";

    public static final String ADD_ACCOUNT_QUERY= "INSERT INTO accounts (balance, client_id) VALUES (?, ?)";
    public static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    public static final String DELETE_ACCOUNT_QUERY = "DELETE FROM accounts WHERE rib = ?";
}
