package com.ipr.resource;

import com.ipr.client.KeycloakTokenClient;
import com.ipr.dto.TokenResponse;
import io.quarkiverse.bucket4j.runtime.RateLimited;
import io.quarkiverse.bucket4j.runtime.resolver.IpResolver;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/auth/token")
public class AuthResource {

    @RestClient
    KeycloakTokenClient keycloakClient;

    @ConfigProperty(name = "keycloak.realm")
    String realm;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RateLimited(bucket = "auth", identityResolver = IpResolver.class)
    @Operation(description = "Используется для получения токена")
    public TokenResponse login(@FormParam("username") String username,
                               @FormParam("password") String password) {
        Form form = new Form()
                .param("grant_type", "password")
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .param("username", username)
                .param("password", password);
        return keycloakClient.getToken(realm, form);
    }

    @POST
    @Path("/client-credentials")
    @Produces(MediaType.APPLICATION_JSON)
    public TokenResponse clientCredentials() {
        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", clientId)
                .param("client_secret", clientSecret);
        return keycloakClient.getToken(realm, form);
    }
}
