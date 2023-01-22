package com.learn.graphql.mutation;

import com.learn.graphql.query.QueryController;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.learn.graphql.records.GraphQLRecords.Customer;

@Controller
public class MutationController {

    private final AtomicInteger id = new AtomicInteger(QueryController.customers.size());

    @MutationMapping
    public Customer addCustomer(@Argument String name) {
        List<Customer> customers = QueryController.customers;
        var newCustomerId = id.incrementAndGet();
        var newCustomer = new Customer(newCustomerId, name);
        customers.add(newCustomer);
        return newCustomer;
    }

    @SchemaMapping(typeName = "Mutation", field = "editCustomer")
    public Customer editCustomer(@Argument int id, @Argument String name) {
        List<Customer> customers = QueryController.customers;
        Optional<Customer> customerOptional = customers.stream().filter(customer -> id == customer.id()).findFirst();
        if (customerOptional.isPresent()) {
            customers.removeIf(customer -> id == customer.id());
            Customer mutatedCustomer = new Customer(id, name);
            customers.add(mutatedCustomer);
            return mutatedCustomer;
        }
        return null;
    }
}
