package com.learn.graphql;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.learn.graphql.records.GraphQLRecords.Account;
import static com.learn.graphql.records.GraphQLRecords.Customer;

@Service
public class CrmService {

    public static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Shravan"));
        customers.add(new Customer(2, "Shanmugam"));
    }

    private final AtomicInteger id = new AtomicInteger(customers.size());

    public Customer updateCustomer(int id, String name) {
        Optional<Customer> customerOptional = customers.stream().filter(customer -> id == customer.id()).findFirst();
        if (customerOptional.isPresent()) {
            customers.removeIf(customer -> id == customer.id());
            Customer mutatedCustomer = new Customer(id, name);
            customers.add(mutatedCustomer);
            return mutatedCustomer;
        }
        return null;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getCustomerById(int id) {
        return customers.stream().filter(customer -> id == customer.id()).findFirst().orElse(null);
    }

    public Account getAccount(Customer customer) {
        System.out.println("getting account for customer " + customer.id());
        return new Account(customer.id(), customer.name() + " - account");
    }

    public Map<Customer, Account> getCustomerAccountMap(List<Customer> customers) {
        return customers.stream().collect(Collectors.toMap(customer1 -> customer1, customer1 -> new Account(customer1.id(), customer1.name() + " - account")));
    }

    public Customer addCustomer(String name) {
        var newCustomerId = id.incrementAndGet();
        var newCustomer = new Customer(newCustomerId, name);
        customers.add(newCustomer);
        return newCustomer;
    }
}
