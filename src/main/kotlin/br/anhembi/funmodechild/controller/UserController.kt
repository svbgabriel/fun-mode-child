package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.PasswordUpdateRequest
import br.anhembi.funmodechild.model.request.UserRequest
import br.anhembi.funmodechild.service.UserService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Users")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun createUser(@RequestBody request: UserRequest) {
        userService.create(request)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun passwordUpdate(@RequestBody body: PasswordUpdateRequest) {
        val loggedUser = getLoggedUser()
        userService.updatePassword(loggedUser.email, body.oldPassword, body.newPassword, body.newPasswordConfirm)
    }
}
