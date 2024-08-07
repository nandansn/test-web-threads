package com.nanda.payment.demo.controller;


import com.nanda.payment.demo.service.RouterServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/router")
public class Router {

    RouterServiceImpl routerService;


}
