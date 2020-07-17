package com.example.people10.service;

import com.example.people10.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService{

    private static Map<Long, CustomerDTO> customerMap = new HashMap<>();

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        customerMap.put(customerDTO.getId(),customerDTO);
        return customerDTO;
    }

    @Override
    public CustomerDTO findCustomerById(Long customerId) {
        return customerMap.get(customerId);
    }

    @Override
    public Boolean validateData(CustomerDTO customerDTO) {
        if(customerDTO.getPassword().length()<8 || customerDTO.getPassword().length()>10)
            return false;
        for (Map.Entry<Long,CustomerDTO> entry : customerMap.entrySet()){
            if(customerDTO.getFirstname().equalsIgnoreCase(entry.getValue().getFirstname()))
                return false;
        }
        return true;
    }
}
