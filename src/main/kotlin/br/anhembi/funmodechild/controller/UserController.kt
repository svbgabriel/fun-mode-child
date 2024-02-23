package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.PasswordUpdateRequest
import br.anhembi.funmodechild.model.request.UserRequest
import br.anhembi.funmodechild.service.UserService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Users", description = "User management endpoints")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "204", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    fun createUser(@Valid @RequestBody request: UserRequest) {
        userService.create(request)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update logged user password")
    @ApiResponse(responseCode = "204", description = "Password updated successfully")
    @ApiResponse(responseCode = "400", description = "Password mismatch or invalid old password")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    fun passwordUpdate(@Valid @RequestBody body: PasswordUpdateRequest) {
        val loggedUser = getLoggedUser()
        userService.updatePassword(loggedUser.email, body.oldPassword, body.newPassword, body.newPasswordConfirm)
    }
}
