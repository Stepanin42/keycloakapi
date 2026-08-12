package com.ipr.client;

import com.ipr.dto.KeycloakUserRepresentation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-admin-api")
@Path("/admin/realms/{realm}/users")
public interface KeycloakAdminClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createUser(@PathParam("realm") String realm,
                        @HeaderParam("Authorization") String bearerToken,
                        KeycloakUserRepresentation user);
}
