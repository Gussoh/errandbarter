/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.errandbarter.server.entity;

import java.io.Serializable;

/**
 *
 * @author Zach
 */
public class User implements Serializable {
    
    private String id;
    private int balance;
    private int disposableBalance;
    private double reliability;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDisposableBalance() {
        return disposableBalance;
    }

    public void setDisposableBalance(int disposableBalance) {
        this.disposableBalance = disposableBalance;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }
    
}
