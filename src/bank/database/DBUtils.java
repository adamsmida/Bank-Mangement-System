package bank.database;

import bank.data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBUtils {

    public static User currentUser;

    public static void login(String username, String password, String role) {
        try {
            System.out.println("Login with username: " + username + " and role: " + role + ".");
            final String query = DBQueries.LOGIN_QUERY;
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                currentUser = new User(id, username, password, firstName, lastName);
                System.out.println("Login successful.");
            } else{
                throw new RuntimeException("Login failed.");
            }
        } catch (SQLException e) {
            System.err.println("Login failed.");
            throw new RuntimeException(e.getMessage());
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static List<Account> getClientAccounts(Long id) {
        final String query = DBQueries.GET_CLIENT_ACCOUNTS_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Account> results = new ArrayList<>();
            while (resultSet.next()) {
                Long accountId = resultSet.getLong("id");
                String rib = resultSet.getString("rib");
                Long balance = resultSet.getLong("balance");
                Long clientId = resultSet.getLong("client_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Account account = new Account(accountId, rib, balance, clientId, firstName, lastName);
                results.add(account);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void deposit(String rib, Long amount) {
        final String query = DBQueries.DEPOSIT_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, amount);
            statement.setObject(2, rib, Types.OTHER);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void withdraw(String rib, Long amount) {
        final String query = DBQueries.WITHDRAW_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, amount);
            statement.setObject(2, rib,Types.OTHER);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static List<Account> getAccounts() {
        final String query = DBQueries.GET_ACCOUNTS_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Account> results = new ArrayList<>();
            while (resultSet.next()) {
                Long accountId = resultSet.getLong("id");
                String rib = resultSet.getString("rib");
                Long balance = resultSet.getLong("balance");
                Long clientId = resultSet.getLong("client_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Account account = new Account(accountId, rib, balance, clientId,firstName,lastName);
                results.add(account);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static List<Account> searchAccount(String rib) {
        final String query = DBQueries.SEARCH_ACCOUNT_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, "%"+rib+"%", Types.OTHER);
            ResultSet resultSet = statement.executeQuery();
            List<Account> result = new ArrayList<>();
           while(resultSet.next()) {
                Long accountId = resultSet.getLong("id");
                String accountRib = resultSet.getString("rib");
                Long balance = resultSet.getLong("balance");
                Long clientId = resultSet.getLong("client_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Account account = new Account(accountId, accountRib, balance, clientId,firstName,lastName);
                result.add(account);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static List<Client> getClients() {
        final String query = DBQueries.GET_CLIENTS_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<Client> results = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Client client = new Client(id, username, password, firstName, lastName);
                results.add(client);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }
    public static User updateUser(User user){
        final String query = DBQueries.UPDATE_USER_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static User getUser(Long id){
        final String query = DBQueries.GET_USER_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                User user = new User(userId, username, password, firstName, lastName);
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void deleteClient(Long id) {
        final String query = DBQueries.DELETE_USER_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void addClient(String firstName, String lastName, String username, String password) {
        final String query = DBQueries.ADD_CLIENT_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void addAccount(Long id, Long balance){
        final String query = DBQueries.ADD_ACCOUNT_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, balance);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

    public static void deleteAccount(String rib) {
        final String query = DBQueries.DELETE_ACCOUNT_QUERY;
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, rib, Types.OTHER);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }
}
