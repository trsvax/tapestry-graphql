package com.trsvax.graphql.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ExecutionResult;
import graphql.GraphQLError;
import graphql.InvalidSyntaxError;
import graphql.servlet.GenericGraphQLError;
import graphql.validation.ValidationError;

public class GraphQLRequestFilter implements HttpServletRequestFilter {

	
	private final Pattern filterPattern;
	private final GraphQLService graphQLService;
    private static final ObjectMapper mapper = new ObjectMapper();



	public GraphQLRequestFilter(@Inject @Symbol(GraphQLSymbols.MAPPING_PREFIX) String filterPath, GraphQLService graphQLService) {
		this.filterPattern = Pattern.compile(filterPath + ".*", Pattern.CASE_INSENSITIVE);
		this.graphQLService = graphQLService;
	}


	@Override
	public boolean service(HttpServletRequest request, HttpServletResponse response, HttpServletRequestHandler handler)
			throws IOException {

		String path = request.getServletPath();
		String pathInfo = request.getPathInfo();

		if (pathInfo != null) path += pathInfo;

		if (filterPattern.matcher(path).matches()) {
			ExecutionResult executionResult = graphQLService.execute(query(request));
			String result = mapper.writeValueAsString(createResultFromDataAndErrors(executionResult.getData(), executionResult.getErrors()));
			response.setContentType("application/json");
			response.setContentLength(result.length());
			response.getOutputStream().write(result.getBytes());
			return true;
		}
		return handler.service(request, response);
	}
	
	private String query(HttpServletRequest request)  {
		try {
			if ( request.getMethod().equals("POST")) {

				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = request.getInputStream().read(buffer)) != -1) {
				    result.write(buffer, 0, length);
				}
				return result.toString("UTF-8");
			} else {
				return "";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Map<String, Object> createResultFromDataAndErrors(Object data, List<GraphQLError> errors) {

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);

        if (errorsPresent(errors)) {
            List<GraphQLError> clientErrors = filterGraphQLErrors(errors);
            if(clientErrors.size() < errors.size()) {
                // Some errors were filtered out to hide implementation - put a generic error in place.
                clientErrors.add(new GenericGraphQLError("Internal Server Error(s) while executing query"));
            }
            result.put("errors", clientErrors);
        }

        return result;
    }

    private boolean errorsPresent(List<GraphQLError> errors) {
        return errors != null && !errors.isEmpty();
    }

    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream().
                filter(error -> error instanceof InvalidSyntaxError || error instanceof ValidationError).
                collect(Collectors.toList());
    }
    

}
