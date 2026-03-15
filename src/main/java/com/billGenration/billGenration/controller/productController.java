package com.billGenration.billGenration.controller;

import com.billGenration.billGenration.model.DTO.AddproductDTO;
import com.billGenration.billGenration.model.DTO.updateProductDTO;
import com.billGenration.billGenration.model.product;
import com.billGenration.billGenration.service.productService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flipmart")
public class productController {

    private final productService productService;

    public productController(productService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody AddproductDTO addproductDTO){

        productService.addProduct(addproductDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Product Added Succesfully");
    }

    @GetMapping("/getAllProducts")
    public List<product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody updateProductDTO updateProductDTO){
        productService.updateProduct(updateProductDTO);

        return ResponseEntity
                .ok("Product updated successfully");
    }

    @DeleteMapping("/deleteProduct/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long prodId){
        productService.deleteProduct(prodId);

        return ResponseEntity
                .ok("Product Deleted successfully");
    }
}
