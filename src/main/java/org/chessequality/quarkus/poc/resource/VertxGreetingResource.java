package org.chessequality.quarkus.poc.resource;

// RxJava 2
import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 11:00:46,680 INFO  [VertxGreetingResource] ##### Entering vertx.setTimer...
 * 11:00:46,681 INFO  [VertxGreetingResource] ##### Left vertx.setTimer.
 * 11:00:47,685 INFO  [VertxGreetingResource] >>>>> message = Hello Quarkus! (1003 ms)
 */
@Path("/vertx/greeting")
public class VertxGreetingResource {

    private final static Logger LOGGER = LoggerFactory.getLogger("VertxGreetingResource");

    @Inject
    Vertx vertx;

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
}
