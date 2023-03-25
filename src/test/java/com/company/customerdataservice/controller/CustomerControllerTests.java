package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerRepository customerRepo;

    private ObjectMapper mapper = new ObjectMapper();

    Customer inputLarry;
    Customer outputLarry;
    Customer outputMary;
    Customer outputJeff;

    List<Customer> goldCustomers;
    List<Customer> texasCustomers;

    @Before
    public void setUp() throws Exception {
        inputLarry = new Customer("Larry", "Smith", "123 Main St", "Collins", "MS", "39322", "Gold");
        outputLarry = new Customer("Larry", "Smith", "123 Main St", "Collins", "MS", "39322", "Gold");
        outputLarry.setCustomerId(1l);
        outputMary = new Customer("Mary", "Johnson", "245 Ross Ave", "Dallas", "TX", "75206", "Silver");
        outputMary.setCustomerId(2l);
        outputJeff = new Customer("Jeff", "Baker", "321 Rodeo Dr", "Los Angeles", "CA", "54321", "Bronze");
        outputJeff.setCustomerId(3l);

        goldCustomers = new ArrayList<>(Arrays.asList(
                outputMary,
                outputJeff
        ));

        texasCustomers = new ArrayList<>(Arrays.asList(
                outputLarry,
                outputMary

        ));

        doReturn(outputLarry).when(customerRepo).save(inputLarry);
        doReturn(Optional.of(outputLarry)).when(customerRepo).findById(1l);
        doReturn(goldCustomers).when(customerRepo).findAllCustomersByLevel("Gold");
        doReturn(texasCustomers).when(customerRepo).findAllCustomersByState("TX");

    }

    @Test
    public void shouldAddCustomerOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputLarry);
        String outputJson = mapper.writeValueAsString(outputLarry);

        mockMvc.perform(post("/customers")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputLarry);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }



    @Test
    public void shouldGetCustomersByLevel() throws Exception {
        String outputJson = mapper.writeValueAsString(goldCustomers);

        mockMvc.perform(get("/customers/level/Gold"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetCustomersByState() throws Exception {
        String outputJson = mapper.writeValueAsString(texasCustomers);

        mockMvc.perform(get("/customers/state/TX"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingCustomer() throws Exception {
        inputLarry.setCustomerId(1l);
        inputLarry.setStreet("123 Main St");

        String inputJson = mapper.writeValueAsString(inputLarry);

        mockMvc.perform(put("/customers")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());



    }

    @Test
    public void shouldRespondWith204WhenDeletingCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());
    }

}
