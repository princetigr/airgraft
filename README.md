# City Suggestions API

### Reference Documentation
HTTP API accepts a `q` parameter with an optional coordinates to search for cities matching the query:

## Running the Application
Based on the below assumptions: 
```
The reviewer of your solution will be a developer, so you can make some assumptions
about the runtime, e.g.
* there will be a recent JVM / nodejs
* the reviewer will be able to run maven/gradle/npm outside an IDE
* the reviewer will have intellij/vscode installed
* docker will be available to run additional services you need (e.g. a database)
```
* Upon checking out the solution from `git`, please cd to the project root and invoke `./mvnw ` or `./gradlew bootRun`. Application will start up and emmit information about the geo-data cache load as seen below:
```
2020-10-14 21:01:40.656  INFO 32109 --- [           main] AutoCompleteDataConfig                   : Loading geo data cache....
2020-10-14 21:01:40.711  INFO 32109 --- [           main] AutoCompleteDataConfig                   : Loaded 7237 geo data
```
* Upon application start up, please use your favorite HTTP client to call a happy path e.g 
```
curl --location --request GET 'localhost:8080/suggestions?q=Lon&latitude=43.70011&longitude=-79.4163'
```

### Application flow
* On application startup, the `AutoCompleteDataConfig` loads geo data from `src/main/resources/data/cities_canada-usa.tsv` once to simulate a cache for later use.
* The `AutoCompleteController` controller accepts all incoming requests of pattern `suggestions?q=Londo&latitude=43.70011&longitude=-79.4163` which then delegates process to the `AutoCompleteServiceImpl` service layer.
* `AutoCompleteUtils` is used to calculate proximity between user's location and city of interest
* `Scoring` mimics a traditional `order by` sorting often supported by traditional RDBMS. Firstly, sort on distance and then by a`String metric` using `Levenshtein_distance` between input parameter and corresponding city.

### Bonus points
● Can you think of more scoring parameters to make the suggestions better? 
* Scoring can be enhanced by using a better distance calculation algorithm such as Google API that takes into consideration driving situations and a more realistic measurement. This would lead to a dependency to a third party API with a cost implication, one could circumvent that by using other scientific methods to derive a more precise calculation. Research needed.
One could also use both proximity factor and string metric to score the accuracy of the suggestions.

● Sketch out a design for per user API keys and billing for our future city-suggestion-service startup. What are the implications for scalability of your
implementation?
* The design is a simple microservices approach that ensures total separation of concern and allows for horizontal scaling. As seen in the diagram, one is able to scale out based on load by simply adding more instances to the stack.
Although the design is framework agnostic, I am a fan of Netflix OSS using Zuul gateway, and Eureka as service registry. 
I like the convenience it guarantees that allows one scale out by simply spinning off more instances that are automatically added to the registry for the gateway to invoke. There are other ways to achieve this with a little DevOps/Scripting if not using the likes of Eureka.
This design also supports intra-process communication, where each services can internally invoke each other without a round-trip to the gateway ( Yes I have seen some implementations like that :)

### API keys and billing typical flow
* The typical flow would be a user would register to have access to the API, a unique secrete and app key is then generated upon account creation with a one-time access link sent to user to reveal API keys.
* Generally, a process to validate the user and then create a plan based on the selected plan, or a standard plan  as default giving quicker access to suggestions API. This process would be determined by a product owner.
* HTTP client requesting access to the suggestions API must include both keys. Typically, as header parameter.
* The light weight security-service will authenticate and validate the request. If access is granted, a route to quota-service is initiated.
* The quota-service is backed by a store, it does a look up to evaluate if key has not exceeded it quota and accept or reject the request with appropriate HTTP response codes.
* The cache helps with performance greatly by bypassing datastore and can typically have eviction times to reload and purge data to disk.
* Every request must be logged for the purpose for analytics and determining quota allocation
* Bonus: API key system to use a multi-key with global quota for enhanced security. If a key gets compromised only that key is affected. Also teams can have access to the API without sharing a single key.


## Disclaimer: 
I am more conversant with JDK8 and 99% of codebase is based on it. 
I have considered modularizing my solution as supported from JDK9 but decided against it as I do not want to make it seem like a knowledge I already have.
I am however going to explore the features the newer JDK versions have to offer. I was also given `3 days` coupled with my 9-5 to deliver this solution.


### Reference Documentation
* [Levenshtein Distance](https://en.wikipedia.org/wiki/Levenshtein_distance)
* [Haversine Formula](https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Eureka Service Registry](https://spring.io/guides/gs/service-registration-and-discovery/)
* [Routing and Filtering](https://spring.io/guides/gs/routing-and-filtering/)

