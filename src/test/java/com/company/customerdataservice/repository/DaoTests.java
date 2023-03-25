package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DaoTests {

    @Autowired
    CustomerRepository customerRepo;

    @Before
    public void setUp() throws Exception {
        customerRepo.deleteAll();

        customerRepo.save(new Customer("Larry", "Smith", "123 Main St", "Collins", "MS", "39322", "Gold"));
        customerRepo.save(new Customer("Mary", "Johnson", "245 Ross Ave", "Dallas", "TX", "75206", "Silver"));
        customerRepo.save(new Customer("Jeff", "Baker", "321 Rodeo Dr", "Los Angeles", "CA", "54321", "Bronze"));
    }

    @Test
    public void shouldFindAllCustomersByState() {
        List<Customer> texasCustomers = customerRepo.findAllCustomersByState("TX");

        assertEquals(1, texasCustomers.size());
    }

    @Test
    public void shouldFindAllCustomersByLevel() {
        List<Customer> goldCustomers = customerRepo.findAllCustomersByLevel("Gold");

        assertEquals(1, goldCustomers.size());

    }

}
