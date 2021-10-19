package devanmejia.todolist.controller

import devanmejia.todolist.service.user.UserService
import devanmejia.todolist.transfer.UserDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/user")
    fun createNewUser(@RequestBody userDTO: UserDTO) =
        UserDTO.from(userService.createNewUser(userDTO))


}
