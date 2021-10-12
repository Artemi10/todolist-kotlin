package devanmejia.todolist.interceptors

import devanmejia.todolist.exception.NotPermittedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DefaultControllerAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }
}

@ControllerAdvice
class NotPermittedControllerAdvice {
    @ExceptionHandler(NotPermittedException::class)
    fun handleException(e: NotPermittedException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }
}
