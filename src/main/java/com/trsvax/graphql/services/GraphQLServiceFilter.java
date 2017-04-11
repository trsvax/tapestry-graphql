package com.trsvax.graphql.services;

import graphql.ExecutionResult;

public interface GraphQLServiceFilter {
	public ExecutionResult execute(String query, GraphQLService delegate);

}
