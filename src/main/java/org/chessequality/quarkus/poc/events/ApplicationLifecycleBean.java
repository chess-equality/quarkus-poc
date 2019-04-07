package org.chessequality.quarkus.poc.events;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ApplicationLifecycleBean {

    private final static Logger LOGGER = LoggerFactory.getLogger("ApplicationLifecycleBean");

    void onStart(@Observes StartupEvent startupEvent) {
        LOGGER.info("##### The application is starting...");
    }

    void onStop(@Observes ShutdownEvent shutdownEvent) {
        LOGGER.info("##### The application is stopping...");
    }
}
