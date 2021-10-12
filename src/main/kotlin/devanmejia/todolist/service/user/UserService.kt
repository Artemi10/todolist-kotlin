package devanmejia.todolist.service.user

import devanmejia.todolist.model.User
import devanmejia.todolist.transfer.UserDTO
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUserByLogin(login: String): User
    fun createNewUser(userDTO: UserDTO): User
    fun renameUser(oldLogin: String, newLogin: String): User
}
