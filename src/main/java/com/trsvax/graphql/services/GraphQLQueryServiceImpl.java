package com.trsvax.graphql.services;

import static graphql.schema.GraphQLObjectType.newObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLObjectType.Builder;
import graphql.schema.GraphQLSchema;


public class GraphQLQueryServiceImpl implements GraphQLQueryService {
	
	private final Map<String, List<GraphQLFieldDefinition>> fields = new HashMap<>();	
	private final Map<String,GraphQLReloadService> reloads = new HashMap<>();
	

	@Override
	public void addFields(GraphQLContributeQuery query, List<GraphQLFieldDefinition> fields) {
		this.fields.put(query.getClass().getName(), fields);
		for ( GraphQLReloadService reload : reloads.values() ) {
			reload.reload();
		}
	}

	@Override
	public GraphQLSchema getSchema() {
		Builder o = newObject();
		o.name("Query");
		for ( List<GraphQLFieldDefinition> fieldDefinitions : fields.values()) {
			o.fields(fieldDefinitions);
		}	
		return GraphQLSchema.newSchema()
        .query(o.build())
        .build();
	}

	@Override
	public void reload(GraphQLReloadService reload) {
		reloads.put(reload.getClass().getName(), reload);
	}

}
