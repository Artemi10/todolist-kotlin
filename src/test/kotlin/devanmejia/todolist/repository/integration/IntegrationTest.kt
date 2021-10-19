package devanmejia.todolist.repository.integration

import devanmejia.todolist.model.User
import devanmejia.todolist.repository.TaskRepository
import devanmejia.todolist.repository.UserRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
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

fun createPostgresContainer() = postgres("postgres") {
    withDatabaseName("postgres")
    withUsername("postgres")
    withPassword("2424285")
    withInitScript("./static/init.sql")
    withExposedPorts(5431)
}

val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")

private fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)

@Testcontainers
@SpringBootTest
class UserRepositoryIntegrationTest{
    @Autowired
    private lateinit var userRepository: UserRepository

    companion object{
        @Container
        val container = createPostgresContainer()
    }

    @Test
    fun `container is up and running`() =
        assertTrue(container.isRunning)

    @Test
    @Transactional
    fun `find user by existing login`(){
        val user = userRepository.findByLogin("devanmejia")
        assertNotEquals(null, user)
        assertEquals("devanmejia", user!!.login)
        assertEquals(DATE_FORMAT.parse("2003-02-03"), user.birthDate)
        assertEquals(5, user.tasks.size)
    }

    @Test
    @Transactional
    fun `return null if user is not found`(){
        val user = userRepository.findByLogin("non-devanmejia")
        assertNull(user)
    }

    @Test
    @Transactional
    fun `hash code test`(){
        val userSet = mutableSetOf<User>()
        val user = User("login3", Date(), mutableListOf())
        val anotherUser = User("login1", Date(), mutableListOf())
        userSet.add(user)
        val savedUser = userRepository.save(user)
        assertTrue(userSet.contains(savedUser))
        assertFalse(userSet.contains(anotherUser))
    }

    @AfterEach
    @Transactional
    fun `clear test database`() = userRepository.deleteByLogin("login3")

}

@Testcontainers
@SpringBootTest
class TaskRepositoryIntegrationTest{
    @Autowired
    private lateinit var taskRepository: TaskRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    private lateinit var user: User

    companion object{
        @Container
        val container = createPostgresContainer()
    }

    @BeforeEach
    fun initUser() = run {
        user = userRepository.findByLogin("devanmejia")
            ?: throw IllegalArgumentException("Cannot find test user")
    }

    @Test
    @Transactional
    fun `find all user tasks by user login`(){
        val tasks = taskRepository.findAllByUserLogin(user.login)
        assertTrue(user.tasks.size == tasks.size
                && user.tasks.containsAll(tasks)
                && tasks.containsAll(user.tasks))
    }

    @Test
    fun `return empty list if user is not found`(){
        val tasks = taskRepository.findAllByUserLogin("devanmejia1")
        assertTrue(tasks.isEmpty())
    }

    @Test
    @Transactional
    fun `find task by id and user login`(){
        val task = taskRepository.findByIdAndUserLogin(3, user.login)
        assertTrue(user.tasks.contains(task))
        assertEquals(3, task!!.id)
    }

    @Test
    fun `return null if task is not found`(){
        val task = taskRepository.findByIdAndUserLogin(12, user.login)
        assertNull(task)
    }

    @Test
    fun `return null if user is not found`(){
        val task = taskRepository.findByIdAndUserLogin(2, "devanmejia23")
        assertNull(task)
    }

    @Test
    fun `delete task by id and user login`(){
        taskRepository.deleteByIdAndUserLogin(2, user.login)
        val task = taskRepository.findByIdAndUserLogin(2, user.login)
        assertNull(task)
    }
}

