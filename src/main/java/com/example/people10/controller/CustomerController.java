package com.example.people10.controller;

import com.example.people10.dto.CustomerDTO;
import com.example.people10.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/api/customer")
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Boolean validation = customerService.validateData(customerDTO);
        if(validation)
            return new ResponseEntity<>(customerService.save(customerDTO), HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/api/customer/{id}")
    public ResponseEntity getSiteDetails(@PathVariable("id") Long customerId) {
        CustomerDTO customerDTO = customerService.findCustomerById(customerId);
        if(customerDTO != null)
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(customerDTO, HttpStatus.NOT_FOUND);
    }
}
