package com.billGenration.billGenration.service;

import com.billGenration.billGenration.Repository.orderRepo;
import com.billGenration.billGenration.Repository.productRepo;
import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DailyReportService {

    private final productRepo productRepo;
    private final orderRepo orderRepo;
    private final NotificationService notificationService;

    public DailyReportService(productRepo productRepo,
                              orderRepo orderRepo,
                              NotificationService notificationService) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.notificationService = notificationService;
    }

    public void sendDailyReport(){
        List<product> products = productRepo.findAll();
        List<order> orders = orderRepo.findAll();

        long totalOrders = orders.size();

        BigDecimal totalRevenue = orders.stream()
                .map(order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        StringBuilder report = new StringBuilder();
        report.append("📊 DAILY REPORT\n\n");
        report.append("Total Orders: ").append(totalOrders).append("\n");
        report.append("Total Revenue: ₹").append(totalRevenue).append("\n\n");

        report.append("Stock Status:\n");

        for (product p : products) {
            report.append(p.getName())
                    .append(" → Stock: ")
                    .append(p.getStock())
                    .append("\n");
        }

        // Send to Admin
        notificationService.sendWhatsApp(
                "+917211152557",
                report.toString()
        );

        notificationService.sendSms(
                "+917211152557",
                "Daily report sent on WhatsApp."
        );

        System.out.println("Daily report sent successfully.");
    }
    }


