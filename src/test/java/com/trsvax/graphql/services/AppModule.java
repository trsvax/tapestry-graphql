package com.trsvax.graphql.services;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.ImportModule;

@ImportModule({GraphQLModule.class})
public class AppModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(GraphQLContributeQuery.class, TapestryQuery.class);
	}

}
