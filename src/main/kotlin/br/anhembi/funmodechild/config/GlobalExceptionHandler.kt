package br.anhembi.funmodechild.config

import br.anhembi.funmodechild.model.common.*
import br.anhembi.funmodechild.model.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [
        OrderNotFoundException::class,
        ProductNotFoundException::class,
        UserNotFoundException::class
    ])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildResponse(HttpStatus.NOT_FOUND, ex, request)
    }

    @ExceptionHandler(value = [
        PasswordNotMatchException::class,
        PaymentException::class
    ])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val details = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid field") }
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Validation failed",
            path = request.requestURI,
            details = details
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred at ${request.requestURI}", ex)
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = "An unexpected error occurred. Please contact support.",
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(errorResponse)
    }

    private fun buildResponse(status: HttpStatus, ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(errorResponse)
    }
}
