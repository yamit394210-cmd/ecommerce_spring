package com.example.ecommerce_backend.controllers;

import com.example.ecommerce_backend.models.Admin;
import com.example.ecommerce_backend.repositories.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminRepository adminRepository;
    public AdminController(AdminRepository adminRepository){
        this.adminRepository=adminRepository;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> login(@RequestBody Admin data){
        Admin result= adminRepository.findByUsernameAndPassword(data.getUsername(), data.getPassword());
        if(result!=null){
//            return "Login Successfully";
            return ResponseEntity.ok(Map.of(
                    "status","Success",
                     "Message","Login Success",
                     "Data",result
            ));
        }else{
            return ResponseEntity.ok(Map.of(
                    "status","Error",
                    "Message","Login Failed"

            ));
        }
    }
}
