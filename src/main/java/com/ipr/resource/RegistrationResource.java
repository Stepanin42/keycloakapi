package com.ipr.resource;

import com.ipr.client.KeycloakAdminClient;
import com.ipr.client.KeycloakTokenClient;
import com.ipr.dto.Credential;
import com.ipr.dto.KeycloakUserRepresentation;
import com.ipr.dto.RegisterRequest;
import com.ipr.dto.TokenResponse;
import io.quarkiverse.bucket4j.runtime.RateLimited;
import io.quarkiverse.bucket4j.runtime.resolver.IpResolver;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/auth/register")
public class RegistrationResource {

    @RestClient
    KeycloakAdminClient adminClient;
    @RestClient
    KeycloakTokenClient tokenClient;

    @ConfigProperty(name = "keycloak.realm") String realm;
    @ConfigProperty(name = "keycloak.admin.client-id") String adminClientId;
    @ConfigProperty(name = "keycloak.admin.client-secret") String adminClientSecret;

    @POST
    @RateLimited(bucket = "auth", identityResolver = IpResolver.class)
    @Operation(description = "Регистрация новых пользователей")
    public Response register(RegisterRequest req) {
        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", adminClientId)
                .param("client_secret", adminClientSecret);
        TokenResponse token = tokenClient.getToken(realm, form);

        var user = new KeycloakUserRepresentation(
                req.getUsername(), req.getEmail(), req.getFirstName(), req.getLastName(),
                true, List.of(new Credential("password", req.getPassword(), false))
        );

        return adminClient.createUser(realm, "Bearer " + token.getAccessToken(), user);
    }
}
