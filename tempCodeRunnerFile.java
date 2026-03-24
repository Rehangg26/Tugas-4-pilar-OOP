import java.util.Date;

// --- ABSTRAKSI & ENKAPSULASI ---
abstract class Transaction {
    private String transactionID;
    protected double amount; // Protected agar bisa diakses subclass

    public Transaction(String transactionID, double amount) {
        this.transactionID = transactionID;
        this.amount = amount;
    }

    // Method abstrak untuk Polimorfisme
    public abstract boolean execute(Account account);
}

// --- PEWARISAN (INHERITANCE) & POLIMORFISME ---
class Withdrawal extends Transaction {
    public Withdrawal(String id, double amount) {
        super(id, amount);
    }

    @Override
    public boolean execute(Account account) {
        if (account.getBalance() >= amount) {
            account.updateBalance(-amount);
            System.out.println("Penarikan berhasil: -" + amount);
            return true;
        }
        System.out.println("Saldo tidak mencukupi!");
        return false;
    }
}

class Deposit extends Transaction {
    public Deposit(String id, double amount) {
        super(id, amount);
    }

    @Override
    public boolean execute(Account account) {
        account.updateBalance(amount);
        System.out.println("Setoran berhasil: +" + amount);
        return true;
    }
}

// --- ENKAPSULASI ---
class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}

class Card {
    private String cardNumber;
    private Date expiryDate;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
        this.expiryDate = new Date(); // Default hari ini
    }

    public boolean validate() {
        System.out.println("Memvalidasi kartu: " + cardNumber);
        return true; 
    }
}

// --- CLASS UTAMA (ATM) ---
public class ATM {
    public void startSession() {
        System.out.println("=== Selamat Datang di ATM ===");
    }

    public void processTransaction(Account account, Transaction tx) {
        tx.execute(account);
        System.out.println("Sisa saldo: " + account.getBalance());
    }

    public void endSession() {
        System.out.println("Silakan ambil kartu Anda. Terima kasih!");
        System.out.println("==============================");
    }

    // Driver Code
    public static void main(String[] args) {
        ATM atm = new ATM();
        Card myCard = new Card("123-456-789");
        Account myAccount = new Account("987654", 5000.0);

        atm.startSession();
        
        if (myCard.validate()) {
            // Contoh Polimorfisme: Transaction bisa berupa Withdrawal atau Deposit
            Transaction tx1 = new Withdrawal("T001", 2000.0);
            atm.processTransaction(myAccount, tx1);

            Transaction tx2 = new Deposit("T002", 1500.0);
            atm.processTransaction(myAccount, tx2);
        }

        atm.endSession();
    }
}