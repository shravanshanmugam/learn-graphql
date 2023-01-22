package com.learn.graphql.query;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.learn.graphql.records.GraphQLRecords.Account;
import static com.learn.graphql.records.GraphQLRecords.Customer;

@Controller
public class QueryController {

    @QueryMapping
    public String hello() {
        return "Hello everyone!";
    }

    @SchemaMapping(typeName = "Query", field = "helloWithName")
    public String helloAlongWithName(@Argument String name) {
        return "Hello " + name;
    }

    public static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Shravan"));
        customers.add(new Customer(2, "Shanmugam"));
    }

    @QueryMapping
    public List<Customer> customers() {
        return customers;
    }

    @QueryMapping
    public Customer customerById(@Argument int id) {
        return customers.stream().filter(customer -> id == customer.id()).findFirst().orElse(null);
    }

    @SchemaMapping(typeName = "Customer")
    public Account account(Customer customer) {
        System.out.println("getting account for customer " + customer.id());
        return new Account(customer.id(), customer.name() + " - account");
    }

    @BatchMapping
    public Map<Customer, Account> accountBatch(List<Customer> customers) {
        System.out.println("getting account for " + customers.size() + " customers");
        return customers.stream().collect(Collectors.toMap(customer1 -> customer1, customer1 -> new Account(customer1.id(), customer1.name() + " - account")));
    }
}
