package devanmejia.todolist.service.token

import devanmejia.todolist.exception.AuthorizationException
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl : TokenService {

    override fun extractUserLogin(request: ServerHttpRequest) =
        request.headers["Authorization"]?.get(0)
            ?: throw AuthorizationException("Token is not provided")

}
