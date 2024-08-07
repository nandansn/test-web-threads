package com.nanda.payment.demo.service;

import com.nanda.payment.demo.entity.Merchant;
import com.nanda.payment.demo.repo.Merchants;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RouterServiceImpl {

    ReentrantReadWriteLock merchantLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock merchantReadLock = merchantLock.readLock();
    ReentrantReadWriteLock.WriteLock merchantWriteLock = merchantLock.writeLock();


    Merchants merchants;

    public RouterServiceImpl() {
        merchants = new Merchants();
    }


    public String getHost(long id) {
        merchantReadLock.lock();
        Optional<Merchant> merchant = null;
        try {
            merchant = merchants.findMerchant(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            merchantReadLock.unlock();
        }

        assert merchant != null;
        return merchant.get().getHost();

    }

    public void setHost(String host, long id) {
        merchantWriteLock.lock();
        Optional<Merchant> merchant = null;
        try {
            merchant = merchants.findMerchant(id);
            merchants.updateHost(host, merchant.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            merchantWriteLock.unlock();
        }

    }
}
