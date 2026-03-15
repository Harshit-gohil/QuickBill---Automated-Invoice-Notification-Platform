package com.billGenration.billGenration.service;

import com.billGenration.billGenration.Repository.orderRepo;
import com.billGenration.billGenration.Repository.orderitemRepo;
import com.billGenration.billGenration.Repository.productRepo;
import com.billGenration.billGenration.Repository.userRepo;
import com.billGenration.billGenration.model.DTO.AddproductDTO;
import com.billGenration.billGenration.model.DTO.updateProductDTO;
import com.billGenration.billGenration.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService {

    @Autowired
    private final orderitemRepo orderitemRepo;
    private final orderRepo orderRepo;
    private final productRepo productRepo;
    private final userRepo userRepo;

    public productService(orderitemRepo orderitemRepo, orderRepo orderRepo, productRepo productRepo, userRepo userRepo) {
        this.orderitemRepo = orderitemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }


    public void addProduct(AddproductDTO addproduct){
        product product=new product();
        product.setName(addproduct.getName());
        product.setPrice(addproduct.getPrice());
        product.setThreshold(addproduct.getThreshold());
        product.setStock(addproduct.getStock());
        product.setGstPercentage(addproduct.getGstPercentage());

        productRepo.save(product);
    }

    public List<product> getProducts(){
        return productRepo.findAll();
    }

    public void updateProduct(updateProductDTO updateProductDTO){
        product product=productRepo.findById(updateProductDTO.getProdId()).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updateProductDTO.getName());
        product.setStock(updateProductDTO.getStock());
        product.setPrice(updateProductDTO.getPrice());
        product.setThreshold(updateProductDTO.getThreshold());
        product.setGstPercentage(updateProductDTO.getGstPercentage());

        productRepo.save(product);
    }

    public void deleteProduct(Long prodId) {

        product product = productRepo.findById(prodId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepo.delete(product);
    }

}
