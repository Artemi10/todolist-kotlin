package devanmejia.todolist.controller

import devanmejia.todolist.service.user.UserService
import devanmejia.todolist.transfer.UserDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService) {

    @PostMapping("/user")
    fun createNewUser(@RequestBody userDTO: UserDTO) =
        UserDTO.from(userService.createNewUser(userDTO))

    @PatchMapping("/user/{login}/login/{newLogin}")
    fun renameUser(@PathVariable login: String, @PathVariable newLogin: String) =
        UserDTO.from(userService.renameUser(login, newLogin))
}
