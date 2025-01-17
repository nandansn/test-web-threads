package com.nanda.payment.demo.entity;

public class Merchant {

  private long id;
  private String host;
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

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
}
