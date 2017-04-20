package com.trsvax.graphql.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

public class DevelopmentModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(GraphQLContributeQuery.class,TapestryQuery.class).withId("TapestryQuery");
	}
	
	   public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {

	        configuration.add(SymbolConstants.PRODUCTION_MODE, false);
	   }

}
