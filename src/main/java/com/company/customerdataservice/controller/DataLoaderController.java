package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoaderController {

    @Autowired
    CustomerRepository repo;

    @GetMapping("/load-data")
    public void loadData() {
        Customer customer = new Customer();
        customer.setFirstName("Larry");
        customer.setLastName("Smith");
        customer.setStreet("123 Main Street");
        customer.setCity("Collins");
        customer.setState("MS");
        customer.setZipcode("39322");
        customer.setLevel("Gold");
        repo.save(customer);

        Customer customer1 = new Customer();
        customer1.setFirstName("Mary");
        customer1.setLastName("Johnson");
        customer1.setStreet("245 Ross Ave");
        customer1.setCity("Dallas");
        customer1.setState("TX");
        customer1.setZipcode("75206");
        customer1.setLevel("Silver");
        repo.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Jeff");
        customer2.setLastName("Baker");
        customer2.setStreet("321 Rodeo Dr");
        customer2.setCity("Los Angeles");
        customer2.setState("CA");
        customer2.setZipcode("54321");
        customer2.setLevel("Bronze");
        repo.save(customer2);

    }
}
