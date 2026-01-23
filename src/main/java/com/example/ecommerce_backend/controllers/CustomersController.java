package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.models.Customers;
import com.example.ecommerce_backend.repositories.CustomersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CustomersController {

    CustomersRepository cr;
    public CustomersController(CustomersRepository cr){
        this.cr=cr;
    }

    @PostMapping("/customers")

    public Customers save(@RequestBody Customers data){
        return cr.save(data);
    }

    @GetMapping("/customers")
    public List<Customers> getAll(){
        return cr.findAll();
    }

    @PutMapping("/customers")
    public Customers update(@RequestBody Customers data)
    {
        return cr.save(data);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> delete(@PathVariable long id)
    {
        cr.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "Status", "Success",
                "Message", "Data Deleted Successfully"
        ));
    }

    @GetMapping("/Customers/{id}")
    public Customers getSingle(@PathVariable Long id){
        return cr.findById(id).orElse(null);
    }

}
