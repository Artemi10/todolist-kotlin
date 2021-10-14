package devanmejia.todolist.repository.jpa

import devanmejia.todolist.model.User
import devanmejia.todolist.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryJPATest(
    @Autowired
    private val userRepository: UserRepository
) {

    @Test
    fun `lazy loading test`(){
        val user = userRepository.findByLogin("devanmejia")
        if (user != null) {
            println(user::class)
        }
    }

    @Test
    fun `hash code test`(){
        val userSet = mutableSetOf<User>()
        val user = User("login", Date(), mutableListOf())
        val anotherUser = User("login1", Date(), mutableListOf())
        userSet.add(user)
        val savedUser = userRepository.save(user)
        assertTrue(userSet.contains(savedUser))
        assertFalse(userSet.contains(anotherUser))
    }
}
