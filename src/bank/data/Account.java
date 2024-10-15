package bank.data;

import java.util.Objects;

public class Account {
    private Long id;
    private String rib;
    private Long balance;
    private Long clientId;
    private String firstName;
    private String lastName;

    public Account(Long id, String rib, Long balance, Long clientId, String firstName, String lastName) {
        this.id = id;
        this.rib = rib;
        this.balance = balance;
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(rib, account.rib);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rib);
    }

    public String getRib() {
        return rib;
    }


    public Long getBalance() {
        return balance;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean canDebit(Long amount) {
        return balance >= amount;
    }
    public void debit(Long amount) throws RuntimeException {
        if(!canDebit(amount)) {
            throw new RuntimeException("Insufficient funds");
        }
        balance -= amount;
    }

    public void credit(Long amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return "Account : "+rib;
    }
}
