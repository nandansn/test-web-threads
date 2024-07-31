package com.nanda.payment.demo.entity;

public class Account {

  private double balance;

  public Account(double balance) {
    this.balance = balance;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    System.out.println("set balance");
    this.balance = balance;
  }
}
