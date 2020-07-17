package com.example.people10.controller;

import com.example.people10.dto.CustomerDTO;
import com.example.people10.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerController.class})
public class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerServiceImpl customerService;

    @Test
    public void shouldSaveCustomer() throws Exception {
        CustomerDTO customerDTO = setData();
        when(customerService.validateData(anyObject())).thenReturn(true);
        when(customerService.save(anyObject())).thenReturn(customerDTO);
        mvc.perform(post("/api/customer").content(asJsonString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotSaveCustomer() throws Exception {
        CustomerDTO customerDTO = setData();
        when(customerService.validateData(anyObject())).thenReturn(false);
        when(customerService.save(anyObject())).thenReturn(customerDTO);
        mvc.perform(post("/api/customer").content(asJsonString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetCustomerDetailsByCustomerId() throws Exception {
        CustomerDTO customerDTO = setData();
        when(customerService.findCustomerById(1l)).
                thenReturn(customerDTO);
        mvc.perform(get("/api/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("firstname", is("firstname")));
        verify(customerService, times(1)).findCustomerById(1l);
    }

    @Test
    public void shouldGetEmptyCustomerDetailsByCustomerId() throws Exception {
        when(customerService.findCustomerById(1l)).
                thenReturn(null);
        mvc.perform(get("/api/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(customerService, times(1)).findCustomerById(1l);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerDTO setData(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1l);
        customerDTO.setFirstname("firstname");
        customerDTO.setLastname("lastname");
        customerDTO.setDob(new Date());
        customerDTO.setEmail("email");
        customerDTO.setPassword("password");
        return customerDTO;
    }
}
