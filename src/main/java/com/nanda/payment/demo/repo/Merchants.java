package com.nanda.payment.demo.repo;

import com.nanda.payment.demo.entity.Account;
import com.nanda.payment.demo.entity.Merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Merchants {

    static List<Merchant> merchants = new ArrayList<>(5);

    static {
        merchants.add(new Merchant(1, new Account(100000.00)));
        merchants.add(new Merchant(2, new Account(10000.00)));
        merchants.add(new Merchant(3, new Account(1000.00)));
        merchants.add(new Merchant(4, new Account(1000.00)));
        merchants.add(new Merchant(5, new Account(1000.00)));
    }

    public Optional<Merchant> findMerchant(long id) {
        Optional<Merchant> merchant = merchants.stream().filter(m -> m.getId() == id).findAny();
        return merchant;
    }

    public boolean updateHost(String host, Merchant merchant) {
        merchant.setHost(host);
        return true;
    }
}
