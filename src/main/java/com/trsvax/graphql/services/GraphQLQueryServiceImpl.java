package com.trsvax.graphql.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLSchema.Builder;

public class GraphQLQueryServiceImpl implements GraphQLQueryService {
	
	private final Map<String, List<GraphQLObjectType>> fields = new HashMap<>();	
	private final Map<String,GraphQLReloadService> reloads = new HashMap<>();
	

	@Override
	public void addFields(GraphQLContributeQuery query, List<GraphQLObjectType> fields) {
		this.fields.put(query.getClass().getName(), fields);
		for ( GraphQLReloadService reload : reloads.values() ) {
			reload.reload();
		}
	}

	@Override
	public GraphQLSchema getSchema() {
		Builder schema = GraphQLSchema.newSchema();
		for ( List<GraphQLObjectType> types : fields.values()) {
			for ( GraphQLObjectType queryType : types ) {
				schema.query(queryType);
			}
		}		
		return schema.build();
	}

	@Override
	public void reload(GraphQLReloadService reload) {
		reloads.put(reload.getClass().getName(), reload);
	}

}
