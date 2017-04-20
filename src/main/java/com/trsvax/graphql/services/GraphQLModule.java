package com.trsvax.graphql.services;

import java.util.List;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.PipelineBuilder;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.slf4j.Logger;

public class GraphQLModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(GraphQLQueryService.class, GraphQLQueryServiceImpl.class);
		binder.bind(HttpServletRequestFilter.class, GraphQLRequestFilter.class).withId("GraphQLRequestFilter");

	}
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void setupSymbols(MappedConfiguration<String, Object> configuration)
	{
		configuration.add(GraphQLSymbols.MAPPING_PREFIX, "/graphql");
	}
	
	public static GraphQLService buildGraphQLService(Logger logger,
			   List<GraphQLServiceFilter> configuration,
			   PipelineBuilder pipelineBuilder,
			   @Autobuild GraphQLServiceTerminator terminator){		
		
		return pipelineBuilder.build(logger, GraphQLService.class, GraphQLServiceFilter.class,
				configuration, terminator);
	}
	
	@Contribute(HttpServletRequestHandler.class)
	public static void httpServletRequestHandler(OrderedConfiguration<HttpServletRequestFilter> configuration,
	                                             @InjectService("GraphQLRequestFilter") HttpServletRequestFilter graphQLRequestFilter)	{
		configuration.add("GraphQLRequestFilter", graphQLRequestFilter, "after:IgnoredPaths", "before:GZIP");
	}

}
