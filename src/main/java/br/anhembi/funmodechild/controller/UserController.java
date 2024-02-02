package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.common.LoggedUser;
import br.anhembi.funmodechild.model.request.PasswordUpdateRequest;
import br.anhembi.funmodechild.model.request.UserRequest;
import br.anhembi.funmodechild.service.UserService;
import br.anhembi.funmodechild.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createUser(@RequestBody UserRequest request) {
        userService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordUpdate(@RequestBody PasswordUpdateRequest body) {
        LoggedUser loggedUser = AuthenticationUtil.getLoggedUser();
        userService.updatePassword(loggedUser.getEmail(), body.oldPassword, body.newPassword, body.newPasswordConfirm);
    }
}
