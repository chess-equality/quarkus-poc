package org.chessequality.quarkus.poc.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * https://quarkus.io/guides/security-guide
 */
@Path("subject")
public class SubjectExposingResource {

    private final static Logger LOGGER = LoggerFactory.getLogger("SubjectExposingResource");

    /**
     * This /subject/secured endpoint requires an authenticated user that has been granted the role "Tester"
     * through the use of the @RolesAllowed("Tester") annotation.
     *
     * The endpoint obtains the user principal from the JAX-RS SecurityContext. This will be non-null for a
     * secured endpoint.
     *
     * @param securityContext JAX-RS SecurityContext
     * @return Name of the principal or "anonymous"
     */
    @GET
    @Path("secured")
    @RolesAllowed("Tester")
    public String getSubjectSecured(@Context SecurityContext securityContext) {

        Principal user = securityContext.getUserPrincipal();

        String name = user != null ? user.getName() : "anonymous";

        return name;
    }

    /**
     * The /subject/unsecured endpoint allows for unauthenticated access by specifying the @PermitAll annotation.
     *
     * This call to obtain the user principal will return null if the caller is unauthenticated, non-null if the
     * caller is authenticated.
     *
     * @param securityContext JAX-RS SecurityContext
     * @return Name of the principal or "anonymous"
     */
    @GET
    @Path("unsecured")
    @PermitAll
    public String getSubjectUnsecured(@Context SecurityContext securityContext) {

        Principal user = securityContext.getUserPrincipal();

        String name = user != null ? user.getName() : "anonymous";

        return name;
    }

    /**
     * The /subject/denied endpoint disallows any access regardless of whether the call is authenticated by
     * specifying the @DenyAll annotation.
     *
     * @param securityContext JAX-RS SecurityContext
     * @return Name of the principal or "anonymous"
     */
    @GET
    @Path("denied")
    @DenyAll
    public String getSubjectDenied(@Context SecurityContext securityContext) {

        Principal user = securityContext.getUserPrincipal();

        String name = user != null ? user.getName() : "anonymous";

        return name;
    }
}
