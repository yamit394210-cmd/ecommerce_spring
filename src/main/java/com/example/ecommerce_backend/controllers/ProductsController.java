package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.models.Products;
import com.example.ecommerce_backend.repositories.ProductsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class ProductsController {
    private ProductsRepository productsRepository;
    public ProductsController(ProductsRepository productsRepository){
        this.productsRepository=productsRepository;
    }

    @PostMapping("/products")
    public Products add(@RequestParam("category") String category,
                        @RequestParam("name") String name,
                        @RequestParam("mrp") String mrp,
                        @RequestParam("saleprice") String saleprice,
                        @RequestParam("description") String description,
                        @RequestParam("image1")MultipartFile image1,
                        @RequestParam("image2") MultipartFile image2
    )throws IOException
    {
        //upload first file
        String foldername = "/uploads";

        String filename1 = System.currentTimeMillis()+"_"+ image1.getOriginalFilename();
        Path path1 = Paths.get(foldername, filename1);
        Files.write(path1, image1.getBytes());

        //upload second file
        String filename2 = System.currentTimeMillis()+"_"+ image2.getOriginalFilename();
        Path path2 = Paths.get(foldername, filename2);
        Files.write(path2, image2.getBytes());

//        Create model oject
        Products data = new Products();
        data.setCategory(category);
        data.setName(name);
        data.setMrp(mrp);
        data.setSaleprice(saleprice);
        data.setDescription(description);
        data.setImage1(filename1);
        data.setImage2(filename2);

        return productsRepository.save(data);

    }
}
