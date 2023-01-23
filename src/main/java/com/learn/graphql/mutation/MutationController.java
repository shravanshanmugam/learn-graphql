package com.learn.graphql.mutation;

import com.learn.graphql.CrmService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.learn.graphql.records.GraphQLRecords.Customer;

@Controller
public class MutationController {

    private final CrmService crmService;

    public MutationController(final CrmService crmService) {
        this.crmService = crmService;
    }

    @MutationMapping
    public Customer addCustomer(@Argument String name) {
        return crmService.addCustomer(name);
    }

    @SchemaMapping(typeName = "Mutation", field = "editCustomer")
    public Customer editCustomer(@Argument int id, @Argument String name) {
        return crmService.updateCustomer(id, name);
    }

}
