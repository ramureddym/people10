package com.example.people10.service;

import com.example.people10.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);
    CustomerDTO findCustomerById(Long id);
    Boolean validateData(CustomerDTO customerDTO);
}
