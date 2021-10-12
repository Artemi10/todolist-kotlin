package devanmejia.todolist.service.user

import devanmejia.todolist.model.User
import devanmejia.todolist.repository.UserRepository
import devanmejia.todolist.transfer.UserDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository) : UserService {

    override fun createNewUser(userDTO: UserDTO): User {
        TODO("Not yet implemented")
    }

    override fun renameUser(oldLogin: String, newLogin: String): User {
        TODO("Not yet implemented")
    }
}
