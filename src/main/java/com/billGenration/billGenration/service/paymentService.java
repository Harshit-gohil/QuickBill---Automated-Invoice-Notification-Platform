package com.billGenration.billGenration.service;


import com.billGenration.billGenration.Repository.orderRepo;
import com.billGenration.billGenration.Repository.orderitemRepo;
import com.billGenration.billGenration.Repository.productRepo;
import com.billGenration.billGenration.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class paymentService {
    @Autowired

    private final orderitemRepo orderitemRepo;
    private final orderRepo orderRepo;
    private final productRepo productRepo;
    private final userRepo userRepo;

    public paymentService(orderitemRepo orderitemRepo, orderRepo orderRepo, productRepo productRepo, userRepo userRepo) {
        this.orderitemRepo = orderitemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    private final Random random=new Random();

    public boolean paymentProcces(){
        int randomNum =random.nextInt(5)+1;

        return randomNum !=1;
    }
}
