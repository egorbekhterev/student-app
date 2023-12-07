package ru.bekhterev.rservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "Сервис аутентификации и авторизации")
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final RestTemplate restTemplate;

    @Value("${keycloak.token-endpoint}")
    private String tokenEndpoint;
    @Value("${jwt.auth.converter.resource-id}")
    private String clientId;

    @PostMapping(value = "/authenticate")
    @Operation(summary = "Получить токен доступа")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        MultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
        credentials.add("grant_type", "password");
        credentials.add("client_id", clientId);
        credentials.add("username", username);
        credentials.add("password", password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String> > request = new HttpEntity<>(credentials, headers);
        try {
            return restTemplate.postForEntity(tokenEndpoint, request, Object.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Username or password is incorrect. Please try again.");
        }
    }
}
