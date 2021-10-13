package devanmejia.todolist.service.token

import devanmejia.todolist.exception.AuthorizationException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class TokenServiceImpl : TokenService {

    override fun extractUserLogin(request: HttpServletRequest) =
        request.getHeader("Authorization")
            ?: throw AuthorizationException("Token is not provided")

}
