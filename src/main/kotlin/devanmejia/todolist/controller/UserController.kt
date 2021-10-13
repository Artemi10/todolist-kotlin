package devanmejia.todolist.controller

import devanmejia.todolist.service.token.TokenService
import devanmejia.todolist.service.user.UserService
import devanmejia.todolist.transfer.UserDTO
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class UserController(
    private val tokenService: TokenService,
    private val userService: UserService) {

    @PostMapping("/user")
    fun createNewUser(@RequestBody userDTO: UserDTO) =
        UserDTO.from(userService.createNewUser(userDTO))

    @PatchMapping("/user/login/{login}")
    fun renameUser(@PathVariable login: String, request: HttpServletRequest) : UserDTO{
        val oldLogin = tokenService.extractUserLogin(request)
        return UserDTO.from(userService.renameUser(oldLogin, login))
    }

}
