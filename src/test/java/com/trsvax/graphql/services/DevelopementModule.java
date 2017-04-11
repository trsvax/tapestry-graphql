package com.trsvax.graphql.services;

import org.apache.tapestry5.ioc.ServiceBinder;

public class DevelopementModule {
	
	public static void bind(ServiceBinder binder) {
		binder.bind(GraphQLContributeQuery.class,TapestryQuery.class).withId("TapestryQuery");
	}

}
