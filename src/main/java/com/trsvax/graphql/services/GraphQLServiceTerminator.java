package com.trsvax.graphql.services;

import static graphql.schema.GraphQLObjectType.newObject;

import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.slf4j.Logger;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLObjectType.Builder;

public class GraphQLServiceTerminator implements GraphQLService, GraphQLReloadService {
	
	private final Logger logger;
	private final GraphQLQueryService graphQLQueryService;
	
	private boolean reload = true;
	private GraphQL graphQL;

	
	public GraphQLServiceTerminator(Logger logger, GraphQLQueryService graphQLQueryService) {
		this.logger = logger;
		this.graphQLQueryService = graphQLQueryService;
	}
	
	@PostInjection
	public void addReload() {
		graphQLQueryService.reload(this);
	}

	@Override
	public ExecutionResult execute(String query) {
		logger.info("query ",query);
		if ( reload ) {
			graphQL = new GraphQL(graphQLQueryService.getSchema());
			reload = false;
		}
        return graphQL.execute(query);
	}	
	
	GraphQLObjectType root() {
		Builder root = newObject().name("Query");
		
		return root.build();
	}

	@Override
	public void reload() {
		reload = true;		
	}

}
