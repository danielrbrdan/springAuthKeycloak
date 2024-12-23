package com.project.base.auth.keycloack;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.base.auth.dto.UserDTO;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.auth-server-url}")
    private String serverURL;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientID;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final Keycloak keycloakAdminClient;

    public AccessTokenResponse login(UserDTO request) {
        try (Keycloak keycloak = this.keycloakCredentialBuilder(request)) {
            return keycloak.tokenManager().getAccessToken();
        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Response createUser(UserDTO request) {
        System.out.println("Creating user: " + request.getUsername());
        UsersResource usersResource = this.keycloakAdminClient.realm(this.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(request.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.singleAttribute("phoneNumber", request.getPhoneNumber());
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credentialRepresentation));

        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == 201) {
                System.out.println("Response Status: " + response.getStatus()); 
                System.out.println("Response Entity: " + response.readEntity(String.class));
                return Response.status(Response.Status.CREATED).build();
            } else {
                System.out.println("Response Status: " + response.getStatus()); 
                System.out.println("Response Entity: " + response.readEntity(String.class));
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Keycloak keycloakCredentialBuilder(UserDTO request) {
        return KeycloakBuilder.builder()
                .realm(this.realm)
                .serverUrl(this.serverURL)
                .clientId(this.clientID)
                .clientSecret(this.clientSecret)
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}