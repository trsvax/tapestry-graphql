package com.trsvax.graphql.services;

import org.slf4j.Logger;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLObjectType.Builder;
import graphql.schema.GraphQLSchema;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class GraphQLServiceImpl implements GraphQLService {
	
	private final Logger logger;
	private final GraphQLSchema schema;
	private final GraphQL graphQL;
	
	public GraphQLServiceImpl(Logger logger) {
		this.logger = logger;
		this.schema = GraphQLSchema.newSchema().query(root()).build();
		this.graphQL = new GraphQL(schema);
	}

	@Override
	public ExecutionResult execute(String query) {
		logger.info("query ",query);
        return graphQL.execute(query);
	}	
	
	GraphQLObjectType root() {
		Builder root = newObject().name("Query");
		
		return root.build();
	}

}
