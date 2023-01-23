package com.learn.graphql.query;

import com.learn.graphql.CrmService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

import static com.learn.graphql.records.GraphQLRecords.Account;
import static com.learn.graphql.records.GraphQLRecords.Customer;

@Controller
public class QueryController {

    private final CrmService crmService;

    public QueryController(final CrmService crmService) {
        this.crmService = crmService;
    }


    @QueryMapping
    public String hello() {
        return "Hello everyone!";
    }

    @SchemaMapping(typeName = "Query", field = "helloWithName")
    public String helloAlongWithName(@Argument String name) {
        return "Hello " + name;
    }

    @QueryMapping
    public List<Customer> customers() {
        return crmService.getCustomers();
    }

    @QueryMapping
    public Customer customerById(@Argument int id) {
        return crmService.getCustomerById(id);
    }

    @SchemaMapping(typeName = "Customer")
    public Account account(Customer customer) {
        return crmService.getAccount(customer);
    }

    @BatchMapping
    public Map<Customer, Account> accountBatch(List<Customer> customers) {
        System.out.println("getting account for " + customers.size() + " customers");
        return crmService.getCustomerAccountMap(customers);
    }

}
