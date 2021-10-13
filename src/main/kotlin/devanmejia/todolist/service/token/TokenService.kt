package devanmejia.todolist.service.token

import org.springframework.http.server.ServerHttpRequest
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
interface TokenService {
    fun extractUserLogin(request: HttpServletRequest): String
}
