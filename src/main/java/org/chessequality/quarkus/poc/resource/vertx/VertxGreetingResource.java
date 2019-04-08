package org.chessequality.quarkus.poc.resource.vertx;

// RxJava 2
import io.vertx.reactivex.core.Vertx;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

@Path("/vertx/greeting")
public class VertxGreetingResource {

    private final static Logger LOGGER = LoggerFactory.getLogger("VertxGreetingResource");

    @Inject
    Vertx vertx;

    /**
     * 11:00:46,680 INFO  [VertxGreetingResource] ##### Entering vertx.setTimer...
     * 11:00:46,681 INFO  [VertxGreetingResource] ##### Left vertx.setTimer.
     * 11:00:47,685 INFO  [VertxGreetingResource] >>>>> message = Hello Quarkus! (1003 ms)
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{name}")
    public CompletionStage<String> greeting(@PathParam("name") String name) {

        // When complete, return the content to the client
        CompletableFuture<String> future = new CompletableFuture<>();

        long start = System.nanoTime();

        // Asynchronous greeting

        LOGGER.info("##### Entering vertx.setTimer...");

        // Delay reply by 1000ms
        vertx.setTimer(1000, l -> {

            // Compute elapsed time in milliseconds
            long duration = MILLISECONDS.convert(System.nanoTime() - start, NANOSECONDS);

            // Format message
            String message = String.format("Hello %s! (%d ms)%n", name, duration);

            // Complete
            LOGGER.info(">>>>> message = {}", message);
            future.complete(message);
        });

        LOGGER.info("##### Left vertx.setTimer.");

        return future;
    }

    /**
     * See js/streaming.js
     *
     * @param name The subject of greeting
     * @return A stream of greetings
     */
    @GET
    @Path("{name}/streaming")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> streamingGreeting(@PathParam("name") String name) {

        return vertx.periodicStream(2000).toFlowable()
                .map(l -> String.format("Hello %s! (%s)%n", name, new Date()));
    }

    @GET
    @Path("{name}/object")
    public JsonObject jsonObject(@PathParam("name") String name) {

        // This works equally well when the JSON result is wrapped in a CompletionStage or a Publisher.
        return new JsonObject().put("Hello", name);
    }

    @GET
    @Path("{name}/array")
    public JsonArray jsonArray(@PathParam("name") String name) {

        // This works equally well when the JSON result is wrapped in a CompletionStage or a Publisher.
        return new JsonArray().add("Hello").add(name);
    }
}
