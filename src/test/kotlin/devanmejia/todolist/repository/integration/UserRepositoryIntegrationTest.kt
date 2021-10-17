package devanmejia.todolist.repository.integration

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import devanmejia.todolist.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.text.SimpleDateFormat
import java.util.*

fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)


@Testcontainers
@SpringBootTest
class UserRepositoryIntegrationTest{
    @Autowired
    private lateinit var userRepository: UserRepository
    private lateinit var user: User

    companion object{
        @Container
        val container = postgres("postgres") {
            withDatabaseName("postgres")
            withUsername("postgres")
            withPassword("2424285")
            withInitScript("./static/init.sql")
            withExposedPorts(5421)
        }
    }

    @BeforeEach
    fun initUser(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val tasks = mutableListOf<Task>()
        user = User("devanmejia", dateFormat.parse("2003-02-03"), tasks)
        tasks.add(Task(Content("Homework", "Do homework until Friday"), true, dateFormat.parse("2021-10-12"), user))
        tasks.add(Task(Content("Extended homework", "Do homework until Sunday"), false, dateFormat.parse("2021-10-12"), user))
        tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
        tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
        tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
        tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
    }

    @Test
    fun `container is up and running`() =
        assertTrue(container.isRunning)

    @Test
    @Transactional
    fun `find user by existing login test`(){
        val user = userRepository.findByLogin("devanmejia")
        assertNotEquals(null, user)
        assertEquals(this.user, user)
        assertTrue(this.user.tasks.size == user!!.tasks.size
                && this.user.tasks.containsAll(user.tasks)
                && user.tasks.containsAll(this.user.tasks))
    }

    @Test
    fun `not find user by non-existing login test`(){
        val user = userRepository.findByLogin("non-devanmejia")
        assertNull(user)
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

