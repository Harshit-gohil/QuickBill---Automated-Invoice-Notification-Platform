package com.billGenration.billGenration.service;

import com.billGenration.billGenration.Repository.orderRepo;
import com.billGenration.billGenration.Repository.orderitemRepo;
import com.billGenration.billGenration.Repository.productRepo;
import com.billGenration.billGenration.Repository.userRepo;
import com.billGenration.billGenration.model.DTO.CreateUserDTO;
import com.billGenration.billGenration.model.users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final orderitemRepo orderitemRepo;
    private final orderRepo orderRepo;
    private final productRepo productRepo;
    private final userRepo userRepo;

    public UserService(orderitemRepo orderitemRepo, orderRepo orderRepo, productRepo productRepo, userRepo userRepo) {
        this.orderitemRepo = orderitemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public users createUser(CreateUserDTO createUserDTO){
        users users=new users();
        users.setName(createUserDTO.getName());
        users.setMobile(createUserDTO.getMobile());
        users.setEmail(createUserDTO.getEmail());
        return userRepo.save(users);
    }
}
