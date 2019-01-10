
No Longer maintained moving to node/graphql/react
---

Start of Tapestry GraphQL integration

There are four parts

First there is an HttpServletRequestFilter which bind to a url (typically graphql) and
calls the GraphQLService with the query

The GraphQLService is a pipeline to allow filters to run before/after the service for authentication, post processing etc. The terminator for the service runs the query. The schema for the query is gathered by the GraphQLQueryService.

The GraphQLQueryService takes root field contributions from various services and provides a list of fields to build the root query. It also manages the reload hub that allows hot reloading of the query for development.

The final part are the GraphQLConributeQuery services. These services just contribute fields to the root query. In order to do this they must be marked @EagerLoad.



