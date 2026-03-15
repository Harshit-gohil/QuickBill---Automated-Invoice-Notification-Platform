package com.billGenration.billGenration.service;

import com.billGenration.billGenration.Repository.orderRepo;
import com.billGenration.billGenration.Repository.orderitemRepo;
import com.billGenration.billGenration.Repository.productRepo;
import com.billGenration.billGenration.Repository.userRepo;
import com.billGenration.billGenration.model.DTO.OrderItemDTO;
import com.billGenration.billGenration.model.DTO.OrderRequestDTO;
import com.billGenration.billGenration.model.enums.OrderStatus;
import com.billGenration.billGenration.model.enums.paymentStatus;
import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.orderitem;
import com.billGenration.billGenration.model.product;
import com.billGenration.billGenration.model.users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceOrder {

    private final orderitemRepo orderitemRepo;
    private final orderRepo orderRepo;
    private final productRepo productRepo;
    private final userRepo userRepo;
    private final invoiceService invoiceService;
    private final paymentService paymentService;
    private  NotificationService notificationService;

    public PlaceOrder(orderitemRepo orderitemRepo, orderRepo orderRepo, productRepo productRepo, userRepo userRepo, invoiceService invoiceService, paymentService paymentService, NotificationService notificationService) {
        this.orderitemRepo = orderitemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    @Transactional
    public order placeOrder(OrderRequestDTO orderRequestDTO){

        users users=userRepo.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalGst = BigDecimal.ZERO;

        List<orderitem> orderitems=new ArrayList<>();

        for(OrderItemDTO orderItemDTO:orderRequestDTO.getItems()){
            product prod=productRepo.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if(prod.getStock() <orderItemDTO.getQuantity()){
                throw new RuntimeException("Insufficient stock for product: " + prod.getName());
            }

            BigDecimal price = prod.getPrice();
            Long quantity = Long.valueOf(orderItemDTO.getQuantity());
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal gstAmount=itemTotal
                    .multiply(BigDecimal.valueOf(prod.getGstPercentage()))
                    .divide(BigDecimal.valueOf(100));

            totalAmount=totalAmount.add(itemTotal);
            totalGst=totalGst.add(gstAmount);

            orderitem orderitem=new orderitem();
            orderitem.setProduct(prod);
            orderitem.setPrice(price);
            orderitem.setQuantity(orderItemDTO.getQuantity());

            orderitems.add(orderitem);
        }

        order order=new order();
        order.setUser(users);
        order.setTotalAmount(totalAmount);
        order.setGstAmount(totalGst);
        order.setPaymentStatus(paymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderitems(orderitems);

        for(orderitem item:orderitems){
            item.setOrder(order);
        }

        order=orderRepo.save(order);

        boolean paymentSuccess=paymentService.paymentProcces();

        if (!paymentSuccess){
            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setPaymentStatus(paymentStatus.FAILED);
            return orderRepo.save(order);
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(paymentStatus.SUCCESS);

        for(orderitem Item:orderitems) {
            product prod = Item.getProduct();
            Long updateStock = prod.getStock() - Item.getQuantity();
            prod.setStock(updateStock);
            productRepo.save(prod);


            if (updateStock <= prod.getThreshold()) {

                String alertMessage = "🚨 LOW STOCK ALERT 🚨\n\n" +
                        "Product: " + prod.getName() + "\n" +
                        "Remaining Stock: " + updateStock;

                notificationService.sendWhatsApp(
                        "+917211152557",
                        alertMessage
                );

                notificationService.sendSms(
                        "+917211152557",
                        alertMessage
                );
            }


        }

        // 🔥 Generate CSV Invoice
        String invoicePath = invoiceService.generateCsvInvoice(order);

        System.out.println("Invoice generated at: " + invoicePath);

        notificationService.sendEmailWithAttachment(
                users.getEmail(),
                "Order Confirmation",
                "Your order " + order.getOrderId() + " is confirmed.",
                invoicePath
        );
        String phoneNumber = "+91" + users.getMobile(); // convert to international format

        notificationService.sendSms(
                phoneNumber,
                "Your order " + order.getOrderId() + " is confirmed."
        );

        notificationService.sendWhatsApp(
                phoneNumber,
                "Your order " + order.getOrderId() + " is confirmed."
        );

        return orderRepo.save(order);
    }
}
