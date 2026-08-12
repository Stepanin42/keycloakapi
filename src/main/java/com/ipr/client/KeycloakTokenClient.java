package com.ipr.client;

import com.ipr.dto.TokenResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "keycloak-token-api")
@Path("/realms/{realm}/protocol/openid-connect/token")
public interface KeycloakTokenClient {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    TokenResponse getToken(@PathParam("realm") String realm, Form form);
}
