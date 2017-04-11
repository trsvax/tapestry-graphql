package com.trsvax.graphql.services;

import org.apache.tapestry5.ioc.ReloadAware;

public interface GraphQLContributeQuery extends ReloadAware {

	@Override
	default boolean shutdownImplementationForReload() {
		// Eager load
		return true;
	}
	
	

}
