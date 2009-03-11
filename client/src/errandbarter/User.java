/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

/**
 *
 * @author Gussoh
 */
public class User {

    private String id;
    private int balance;
    private int disposableBalance;
    private double reliability;

    public User(String user, int balance, int disposableBalance, double reliability) {
        this.id = user;
        this.balance = balance;
        this.disposableBalance = disposableBalance;
        this.reliability = reliability;
    }

    public int getBalance() {
        return balance;
    }

    public int getDisposableBalance() {
        return disposableBalance;
    }

    public String getId() {
        return id;
    }

    public double getReliability() {
        return reliability;
    }
}
