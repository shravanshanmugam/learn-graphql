package com.learn.graphql.records;

public class GraphQLRecords {
    public record Customer(int id, String name){
    }
    public record Account(int id, String name){}
}
