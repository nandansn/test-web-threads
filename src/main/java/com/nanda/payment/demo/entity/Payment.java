package com.nanda.payment.demo.entity;


import jakarta.persistence.Entity;


public class Payment {

    private Long id;
    private Merchant merchant;
    private String sender;
    private String receiver;
    private Double amount;

    public Payment(Merchant merchant, Long id, String sender, String receiver, Double amount) {
        this.merchant = merchant;
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                '}';
    }

    // Getters and Setters
}

