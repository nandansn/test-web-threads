package com.nanda.payment.demo.entity;

public class Merchant {

  private long id;
  private Account account;

  public Merchant(long id, Account account) {
    this.id = id;
    this.account = account;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
