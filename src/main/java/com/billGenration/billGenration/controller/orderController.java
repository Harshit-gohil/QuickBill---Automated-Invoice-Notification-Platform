package com.billGenration.billGenration.controller;

import com.billGenration.billGenration.model.DTO.OrderRequestDTO;
import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.service.PlaceOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flipmart")
public class orderController {
    private final PlaceOrder placeOrder;

    public orderController(PlaceOrder placeOrder) {
        this.placeOrder = placeOrder;
    }

    @PostMapping("/placeOrder")
    public order placeOrder(@RequestBody OrderRequestDTO DTO){
        return placeOrder.placeOrder(DTO);
    }
}
