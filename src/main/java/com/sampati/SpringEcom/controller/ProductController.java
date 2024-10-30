package com.sampati.SpringEcom.controller;

import com.sampati.SpringEcom.model.Product;
import com.sampati.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getAllproducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product=productService.getProductById(id);
        if(product.getId()>0) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product savedProduct=null;
        try{
            savedProduct=productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        }catch(IOException e){
            return new ResponseEntity<>(savedProduct,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product=productService.getProductById(productId);
        if(product.getId()>0) {
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product updatedProduct=null;
        try{
            updatedProduct=productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("updated Successfully",HttpStatus.CREATED);
        }catch(IOException e){
            return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
       Product product= productService.getProductById(id);
       if(product!=null){
           productService.deleteProduct(id);
           return new ResponseEntity("Deleted",HttpStatus.OK);
       }
       else {
           return new ResponseEntity(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/products/search")
    public ResponseEntity <List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> product=productService.searchProduct(keyword);
        return new ResponseEntity(product,HttpStatus.OK);
    }


}
