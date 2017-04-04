package com.trsvax.graphql.services;

import graphql.ExecutionResult;

public interface GraphQLService {
	
	public ExecutionResult execute(String query);

}
