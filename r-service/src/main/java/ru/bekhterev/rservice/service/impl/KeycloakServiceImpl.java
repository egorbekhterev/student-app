package ru.bekhterev.rservice.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bekhterev.rservice.service.KeycloakService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Override
    public void createUser(String username, String password) {
        UsersResource userResource = this.getUserResource();
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEnabled(true);
        keycloakUser.setClientRoles(Map.of("USER", List.of("client_user")));
        CredentialRepresentation credentials = this.addCredentials(password);
        keycloakUser.setUsername(username);
        keycloakUser.setCredentials(List.of(credentials));
        try (Response response = userResource.create(keycloakUser)) {
            String createdId = CreatedResponseUtil.getCreatedId(response);
            log.info("User with id '{}' created", createdId);
        }
    }

    private CredentialRepresentation addCredentials(String password) {
        CredentialRepresentation credentials =  new CredentialRepresentation();
        credentials.setValue(password);
        credentials.setTemporary(false);
        credentials.setType(CredentialRepresentation.PASSWORD);
        return credentials;
    }

    private UsersResource getUserResource() {
        RealmResource realm = keycloak.realm(keycloakRealm);
        return realm.users();
    }
}
