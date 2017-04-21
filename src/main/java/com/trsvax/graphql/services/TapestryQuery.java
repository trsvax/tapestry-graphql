package com.trsvax.graphql.services;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;


@EagerLoad
public class TapestryQuery implements GraphQLContributeQuery {
	
	private final ServiceActivityScoreboard scoreboard;
	

	public TapestryQuery(GraphQLQueryService graphQLQueryService,
			ServiceActivityScoreboard scoreboard) {
		graphQLQueryService.addFields(this, fields());
		this.scoreboard = scoreboard;
		ServiceActivity activity;
	}
	
	@PostInjection
	void addFields(GraphQLQueryService graphQLQueryService) {
		graphQLQueryService.addFields(this, fields());
	}
	
	List<GraphQLFieldDefinition> fields() {
		List<GraphQLFieldDefinition> fields = new ArrayList<>();
		
		fields.add(newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world").build());
		
		fields.add(newFieldDefinition()
				.type(new GraphQLList(activity()))
				.name("ServiceActivity")
				.dataFetcher(activityFetcher())
				.build());
						
		return fields;
	}
	
	
	DataFetcher activityFetcher() {
		return new DataFetcher() {
			
			@Override
			public Object get(DataFetchingEnvironment environment) {
				return scoreboard.getServiceActivity();
			}
		};
	}

	GraphQLObjectType activity() {
		return newObject()
				.name("ServiceActivity")
				.field(newFieldDefinition().type(GraphQLString).name("serviceId"))
				.field(newFieldDefinition().type(GraphQLString).name("scope"))
				.field(newFieldDefinition().type(GraphQLString).name("status"))


				.build();
	}
	

}
