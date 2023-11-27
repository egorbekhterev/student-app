package ru.bekhterev.rservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bekhterev.rservice.service.KeycloakService;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Сервис для взаимодействия с пользователями")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping
    public void createUser(@RequestParam String username, @RequestParam String password) {
        keycloakService.createUser(username, password);
    }
}
