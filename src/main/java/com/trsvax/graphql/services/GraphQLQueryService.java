package com.trsvax.graphql.services;

import java.util.List;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;

public interface GraphQLQueryService {
	
	public void addFields(GraphQLContributeQuery query, List<GraphQLFieldDefinition> fields);
	
	public GraphQLSchema getSchema();
	
	public void reload(GraphQLReloadService reload);

}
