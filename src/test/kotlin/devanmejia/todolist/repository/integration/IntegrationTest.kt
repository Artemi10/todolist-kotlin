package devanmejia.todolist.repository.integration

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import devanmejia.todolist.repository.TaskRepository
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


fun createBasicEntity(): User{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val tasks = mutableListOf<Task>()
    val user = User("devanmejia", dateFormat.parse("2003-02-03"), tasks)
    tasks.add(Task(Content("Homework", "Do homework until Friday"), true, dateFormat.parse("2021-10-12"), user))
    tasks.add(Task(Content("Extended homework", "Do homework until Sunday"), false, dateFormat.parse("2021-10-12"), user))
    tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
    tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
    tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
    tasks.add(Task(Content("Homework Again", "Do homework again until Saturday"), false, dateFormat.parse("2021-10-13"), user))
    return user
}

fun createPostgresContainer() = postgres("postgres") {
    withDatabaseName("postgres")
    withUsername("postgres")
    withPassword("2424285")
    withInitScript("./static/init.sql")
    withExposedPorts(5421)
}

private fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)

@Testcontainers
@SpringBootTest
class UserRepositoryIntegrationTest{
    @Autowired
    private lateinit var userRepository: UserRepository
    private lateinit var user: User

    companion object{
        @Container
        val container = createPostgresContainer()
    }

    @BeforeEach
    fun initUser() = run { user = createBasicEntity() }

    @Test
    fun `container is up and running`() =
        assertTrue(container.isRunning)

    @Test
    @Transactional
    fun `find user by existing login`(){
        val user = userRepository.findByLogin("devanmejia")
        assertNotEquals(null, user)
        assertEquals(this.user, user)
        assertTrue(this.user.tasks.size == user!!.tasks.size
                && this.user.tasks.containsAll(user.tasks)
                && user.tasks.containsAll(this.user.tasks))
    }

    @Test
    fun `return null if user is not found`(){
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

@Testcontainers
@SpringBootTest
class TaskRepositoryIntegrationTest{
    @Autowired
    private lateinit var taskRepository: TaskRepository
    private lateinit var user: User

    companion object{
        @Container
        val container = createPostgresContainer()
    }

    @BeforeEach
    fun initUser() = run { user = createBasicEntity() }

    @Test
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
    fun `find task by id and user login`(){
        val task = taskRepository.findByIdAndUserLogin(2, user.login)
        assertEquals(user.tasks[0], task)
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

