package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.models.Category;
import com.example.ecommerce_backend.repositories.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @PostMapping("/category")
    public Category insert(@RequestBody Category data){
        return categoryRepository.save(data);
    }

    @GetMapping("/category")
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
    @GetMapping("/category/{id}")
    public Category getSingle(@PathVariable long id){
       return categoryRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "Status","Success",
                "Message","Category Deleted Successfully"
        ));
    }

    @PutMapping("/category")
    public Category updateCategory(@RequestBody Category data){
        return categoryRepository.save(data);
    }
}
