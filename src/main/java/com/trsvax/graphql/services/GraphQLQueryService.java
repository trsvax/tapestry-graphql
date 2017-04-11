package com.trsvax.graphql.services;

import graphql.schema.GraphQLSchema;

public interface GraphQLQueryService {
	
	public void addFields();
	
	public GraphQLSchema getSchema();
	
	public void reload(GraphQLReloadService reload);

}
