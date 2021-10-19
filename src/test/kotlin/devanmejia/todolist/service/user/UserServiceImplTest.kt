package devanmejia.todolist.service.user

import devanmejia.todolist.model.User
import devanmejia.todolist.repository.UserRepository
import devanmejia.todolist.transfer.UserDTO
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.text.SimpleDateFormat

@SpringBootTest
internal class UserServiceImplTest {
    private val userRepository = Mockito.spy(UserRepository::class.java)
    private val userService = UserServiceImpl(userRepository)

    companion object{
        val DATE_FORMAT = SimpleDateFormat("dd-MM-YYYY")
    }

    @BeforeEach
    fun initMock(){
        val user =  User("devanmejia", DATE_FORMAT.parse("03-02-2003"), mutableListOf())
        val newUser =  User("devanmejia1", DATE_FORMAT.parse("03-02-2003"), mutableListOf())
        Mockito.`when`(userRepository.findByLogin("devanmejia"))
            .thenReturn(user)
        Mockito.`when`(userRepository.findByLogin("devanmejia1"))
            .thenReturn(null)
        Mockito.`when`(userRepository.save(user))
            .thenReturn(user)
        Mockito.`when`(userRepository.save(newUser))
            .thenReturn(newUser)
    }

    @Test
    fun `create new user if not exists`() {
        val defunctUserDTO = UserDTO(null, "devanmejia1", DATE_FORMAT.parse("03-02-2003"))
        val user = userService.createNewUser(defunctUserDTO)
        assertEquals(defunctUserDTO.id, user.id)
        assertEquals(defunctUserDTO.login, user.login)
        assertEquals(defunctUserDTO.birthDate, user.birthDate)
        assertEquals(0, user.tasks.size)
        Mockito.verify(userRepository, Mockito.times(1))
            .findByLogin("devanmejia1")
        Mockito.verify(userRepository, Mockito.times(1))
            .save(user)
    }

    @Test
    fun `throws exception if exists during creation`() {
        val existsUserDTO = UserDTO(null, "devanmejia", DATE_FORMAT.parse("03-02-2003"))
        assertThrows(IllegalArgumentException::class.java) {
            userService.createNewUser(existsUserDTO)
        }
        Mockito.verify(userRepository, Mockito.times(1))
            .findByLogin("devanmejia")
    }
}
