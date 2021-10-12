package devanmejia.todolist.service.user

import devanmejia.todolist.model.User
import devanmejia.todolist.repository.UserRepository
import devanmejia.todolist.transfer.UserDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository) : UserService {

    override fun createNewUser(userDTO: UserDTO): User {
        val user = User(userDTO.id, userDTO.login, userDTO.birthDate, emptyList())
        return userRepository.save(user)
    }

    override fun renameUser(oldLogin: String, newLogin: String): User {
        val user = userRepository.findByLogin(oldLogin)
            ?: throw IllegalArgumentException("No such user $oldLogin")
        user.login = newLogin
        return userRepository.save(user)
    }
}
