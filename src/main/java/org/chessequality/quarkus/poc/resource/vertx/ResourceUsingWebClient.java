package org.chessequality.quarkus.poc.resource.vertx;

// Axle
import io.vertx.axle.core.Vertx;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.axle.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

/**
 * This resource creates a WebClient and upon request use this client to invoke the https://swapi.co/ API.
 * Depending on the result the response is forwarded as it’s received, or a new JSON object is created with
 * the status and body. The WebClient is obviously asynchronous (and non-blocking), to the endpoint returns
 * a CompletionStage.
 */
@Path("/swapi")
public class ResourceUsingWebClient {

    @Inject
    Vertx vertx;

    private WebClient webClient;

    @PostConstruct
    void initialize() {

        this.webClient = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("swapi.co").setDefaultPort(443).setSsl(true));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public CompletionStage<JsonObject> getStarWarsCharacter(@PathParam("id") int id) {

        return webClient.get("/api/people/" + id)
                .send()
                .thenApply(resp -> {

                    if (resp.statusCode() == 200) {

                        return resp.bodyAsJsonObject();

                    } else {

                        return new JsonObject()
                                .put("code", resp.statusCode())
                                .put("message", resp.bodyAsString());
                    }
                });
    }
}
