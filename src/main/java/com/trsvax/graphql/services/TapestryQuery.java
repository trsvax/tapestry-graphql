package com.trsvax.graphql.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.PostInjection;

import graphql.schema.GraphQLObjectType;

import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;


@EagerLoad
public class TapestryQuery implements GraphQLContributeQuery {
	

	public TapestryQuery(GraphQLQueryService graphQLQueryService) {
		graphQLQueryService.addFields(this, fields());

	}
	
	@PostInjection
	void addFields(GraphQLQueryService graphQLQueryService) {
		graphQLQueryService.addFields(this, fields());
	}
	
	List<GraphQLObjectType> fields() {
		List<GraphQLObjectType> fields = new ArrayList<>();
		
		GraphQLObjectType queryType = newObject()
				.name("helloWorldQuery")
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world")).build();
		
		fields.add(queryType);
				
		return fields;
	}
	
	

}
