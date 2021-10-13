package devanmejia.todolist.service.token

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service

@Service
interface TokenService {
    fun extractUserLogin(request: ServerHttpRequest): String
}
