package com.trsvax.graphql.services;

import java.util.List;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.services.PipelineBuilder;
import org.slf4j.Logger;

public class GraphQLModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(GraphQLContributeQuery.class,TapestryQuery.class).withId("TapestryQuery");
	}
	
	public static GraphQLService buildGraphQLService(Logger logger,
			   List<GraphQLServiceFilter> configuration,
			   PipelineBuilder pipelineBuilder,
			   @Autobuild GraphQLServiceTerminator terminator){		
		
		return pipelineBuilder.build(logger, GraphQLService.class, GraphQLServiceFilter.class,
				configuration, terminator);
	}

}
