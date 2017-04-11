package com.trsvax.graphql.services;

import org.apache.tapestry5.ioc.annotations.EagerLoad;

@EagerLoad
public class TapestryQuery implements GraphQLContributeQuery {
	

	public TapestryQuery(GraphQLQueryService graphQLQueryService) {
		graphQLQueryService.addFields();
	}
	
	

}
