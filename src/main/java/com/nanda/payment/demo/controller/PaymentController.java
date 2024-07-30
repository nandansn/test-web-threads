package com.nanda.payment.demo.controller;

import com.nanda.payment.demo.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping
    @ResponseBody
    public String createPayment(@RequestBody Payment payment) {


        return new Payment(10l, "nanda", "kk",20.00).toString();
    }


}