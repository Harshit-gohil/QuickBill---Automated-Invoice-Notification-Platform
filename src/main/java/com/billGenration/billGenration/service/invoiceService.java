package com.billGenration.billGenration.service;

import com.billGenration.billGenration.model.order;
import com.billGenration.billGenration.model.orderitem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;

@Service
public class invoiceService {
    public String generateCsvInvoice(order order) {

        try {

            // Create folder if not exists
            File directory = new File("invoices");
            if (!directory.exists()) {
                directory.mkdir();
            }

            String fileName = "invoices/invoice_" + order.getOrderId() + ".csv";

            FileWriter writer = new FileWriter(fileName);

            // Header
            writer.append("Order ID,Product,Quantity,Price,GST\n");

            // Add each product
            for (orderitem item : order.getOrderitems()) {

                writer.append(order.getOrderId().toString()).append(",");
                writer.append(item.getProduct().getName()).append(",");
                writer.append(String.valueOf(item.getQuantity())).append(",");
                writer.append(item.getPrice().toString()).append(",");
                writer.append(String.valueOf(item.getProduct().getGstPercentage())).append("\n");
            }

            // Totals
            writer.append("\nTotal Amount:,")
                    .append(order.getTotalAmount().toString())
                    .append("\n");

            writer.append("Total GST:,")
                    .append(order.getGstAmount().toString())
                    .append("\n");

            writer.close();

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV invoice");
        }
    }
}
