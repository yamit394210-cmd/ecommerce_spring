package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.models.Products;
import com.example.ecommerce_backend.repositories.ProductsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
        String foldername = "uploads/";

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

    @GetMapping("/products")
    public List<Products> getAll(){
        return productsRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Products getSingle(@PathVariable Long id){
        return productsRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productsRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "Status", "Success",
                "Message", "Data Deleted Successfully"
        ));
    }


    @PutMapping("/products")
    public Products update(
            @RequestParam("id") String id,
            @RequestParam("category") String category,
            @RequestParam("name") String name,
            @RequestParam("mrp") String mrp,
            @RequestParam("saleprice") String saleprice,
            @RequestParam("description") String description,
            @RequestParam(value ="image1", required = false) MultipartFile image1,
            @RequestParam(value ="image2", required = false) MultipartFile image2
    ) throws IOException{


        String filename1 = null;
        String filename2  = null;
        String folder = "uploads/";

        //Second File
        if(image1!=null){
            filename1 = System.currentTimeMillis()+"-"+ image1.getOriginalFilename();
            Path paths1= Paths.get(folder, filename1);
            Files.write(paths1, image1.getBytes());
        }


        //Second file
        if(image2!=null)
        {
            filename2 = System.currentTimeMillis()+"-"+ image2.getOriginalFilename();
            Path paths2 = Paths.get(folder, filename2);
            Files.write(paths2, image2.getBytes());

        }
        Products data = productsRepository.findById(Long.parseLong(id)).orElse(null);
        if(data!=null){

            data.setName(name);
            data.setMrp(mrp);
            data.setSaleprice(saleprice);
            data.setDescription(description);
            data.setCategory(category);
            if(filename1!=null){
                data.setImage1(filename1);
            }
            if(filename2 != null){
                data.setImage2(filename2);
            }

        }

        return productsRepository.save(data);
    }


}
