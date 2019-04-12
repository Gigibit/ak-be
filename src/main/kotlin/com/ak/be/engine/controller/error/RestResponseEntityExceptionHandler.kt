package com.ak.be.engine.controller.error

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(IllegalArgumentException::class), (IllegalStateException::class)])
    protected fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = ObjectMapper().writeValueAsString(AkError(ex.localizedMessage))
        return handleExceptionInternal(ex, bodyOfResponse,
                HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}