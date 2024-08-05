package com.nanda.payment.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanda.payment.demo.entity.Account;
import com.nanda.payment.demo.entity.Merchant;
import com.nanda.payment.demo.entity.Payment;
import com.nanda.payment.demo.entity.PaymentResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/payments")
public class PaymentController {


  static List<Merchant> merchants = new ArrayList<>(5);

  static ConcurrentHashMap<Long, Object> lockMaps = new ConcurrentHashMap<>();

  private ConcurrentHashMap<Long, AtomicLong> merchantBalances = new ConcurrentHashMap<>();

  static ExecutorService executorService = Executors.newFixedThreadPool(100);

  static {
    merchants.add(new Merchant(1, new Account(100000.00)));
    merchants.add(new Merchant(2, new Account(10000.00)));
    merchants.add(new Merchant(3, new Account(1000.00)));
    merchants.add(new Merchant(4, new Account(1000.00)));
    merchants.add(new Merchant(5, new Account(1000.00)));
  }

  @PostMapping("/withdraw")
  @ResponseBody
  public String createPayment(@RequestBody Payment payment) throws InterruptedException {

    Long merchantId = payment.getMerchant().getId();

    Optional<Merchant> merchant = Optional.ofNullable(
        merchants.stream().filter(m -> merchantId.equals(m.getId())).findAny()
            .orElseThrow(() -> new RuntimeException("Merchant Not Found")));

    Object lockObject = lockMaps.computeIfAbsent(merchant.get().getId(), key -> new Object());


    // process async
    // send notification

    CompletableFuture.runAsync(() -> {
      synchronized (lockObject) {
        double balance = merchant.get().getAccount().getBalance() - payment.getAmount();
        System.out.println("Initial balance: " + merchant.get().getAccount().getBalance());
        System.out.println("Payment amount: " + payment.getAmount());
        System.out.println("Calculated balance after deduction: " + balance);
        if (balance >= 0) {
          System.out.println("Processing payout:"+balance);

          try {
            Thread.sleep(10000);
            merchant.get().getAccount().setBalance(balance);
            System.out.println(merchant.get().getAccount().getBalance());
            System.out.println(LocalDateTime.now());
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }

        } else {
          throw new RuntimeException("Merchant balance is low");
        }
      }

    }, executorService);

    ObjectMapper om = new ObjectMapper();
    String paymentMessage = "";
    try {
      paymentMessage = om.writeValueAsString(new PaymentResponse("On Process"));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return paymentMessage;


  }


  @PostMapping("lockfree/withdraw")
  @ResponseBody
  public String createWithdraw(@RequestBody Payment payment) throws InterruptedException {

    Long merchantId = payment.getMerchant().getId();

    Optional<Merchant> merchant = Optional.ofNullable(
            merchants.stream().filter(m -> merchantId.equals(m.getId())).findAny()
                    .orElseThrow(() -> new RuntimeException("Merchant Not Found")));

    AtomicLong balance = merchantBalances.computeIfAbsent(merchantId, this::loadBalanceFromDatabase);


    // process async
    // send notification


    ObjectMapper om = new ObjectMapper();
    String paymentMessage = "";
    try {
      paymentMessage = om.writeValueAsString(new PaymentResponse("On Process"));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return paymentMessage;


  }

  private AtomicLong loadBalanceFromDatabase(long merchantId) {

      return new AtomicLong((long) merchants.stream().filter(merchant -> merchant.getId() == merchantId).findAny().get().getAccount().getBalance());


  }

  @GetMapping("/balance/{id}")
  @ResponseBody
  public String getBalance(@PathVariable String id) {
    System.out.println("id:" + id);

    Optional<Merchant> merchant = Optional.ofNullable(
        merchants.stream().filter(m -> Long.parseLong(id) == m.getId()).findAny()
            .orElseThrow(() -> new RuntimeException("Merchant Not Found")));

    double balance = merchant.get().getAccount().getBalance();

    ObjectMapper om = new ObjectMapper();
    String balanceMessage = "";
    try {
      balanceMessage = om.writeValueAsString(balance);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return balanceMessage;
  }


}