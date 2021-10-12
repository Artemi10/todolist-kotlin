package devanmejia.todolist.service.user

import devanmejia.todolist.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository) : UserService {

}
