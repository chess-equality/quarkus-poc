package org.chessequality.quarkus.poc.resource;

import org.chessequality.quarkus.poc.service.GreetingService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/greeting")
public class GreetingResource {

    @ConfigProperty(name = "greeting.message")
    private String message;

    @ConfigProperty(name = "greeting.suffix", defaultValue="!")
    private String suffix;

    @ConfigProperty(name = "greeting.name")
    private Optional<String> name;

    @Inject
    private GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello/{name}")
    public CompletionStage<String> hello(@PathParam("name") String name) {

        return greetingService.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> greeting() {

        return CompletableFuture.supplyAsync(() -> message + " " + name.orElse("world") + suffix);
        // return CompletableFuture.supplyAsync(() -> "Hello");
    }
}