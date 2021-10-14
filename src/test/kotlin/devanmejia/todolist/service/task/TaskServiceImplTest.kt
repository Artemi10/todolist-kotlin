package devanmejia.todolist.service.task

import devanmejia.todolist.exception.NotPermittedException
import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import devanmejia.todolist.repository.TaskRepository

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat

@SpringBootTest
@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
class TaskServiceImplTest {
    private val taskRepository: TaskRepository = Mockito.spy(TaskRepository::class.java)
    private val taskService: TaskService = TaskServiceImpl(taskRepository)

    @BeforeEach
    fun initMocks(){
        val (activeTask, readyTask) = initializeTasks()
        Mockito.`when`(taskRepository.findByIdAndUserLogin(1, "devanmejia"))
            .thenReturn(activeTask)
        Mockito.`when`(taskRepository.findByIdAndUserLogin(2, "devanmejia"))
            .thenReturn(readyTask)
        Mockito.`when`(taskRepository.save(activeTask))
            .thenReturn(activeTask)
        Mockito.`when`(taskRepository.save(readyTask))
            .thenReturn(readyTask)
    }

    private fun initializeTasks(): Pair<Task, Task>{
        val dateFormat = SimpleDateFormat("dd-MM-YYYY")
        val user = User("devanmejia", dateFormat.parse("02-02-2003"), mutableListOf())
        val content = Content("Test task", "Creating simple task to test")
        val activeTask = Task(content, false, dateFormat.parse("13-10-2021"), user)
        val readyTask = Task(content, true, dateFormat.parse("13-10-2021"), user)
        user.tasks.add(activeTask)
        user.tasks.add(readyTask)
        return Pair(activeTask, readyTask)
    }

    @Test
    fun `close active task test`() {
        val closedTask = taskService.closeTask(1, "devanmejia")
        assertTrue(closedTask.isReady)
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(1, "devanmejia")
        Mockito.verify(taskRepository, Mockito.times(1))
            .save(closedTask)
    }

    @Test
    fun `throws exception while closing if task has already been closed`(){
        assertThrows(IllegalArgumentException::class.java) { taskService.closeTask(2, "devanmejia") }
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(2, "devanmejia")
    }

    @Test
    fun `throws exception while closing if there is not task with necessary id`(){
        assertThrows(NotPermittedException::class.java) { taskService.closeTask(3, "devanmejia") }
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(3, "devanmejia")
    }

    @Test
    fun `change active task content`() {
        val newContent = Content("Changed title", "Changed text")
        val changedTask = taskService.changeContent(1, newContent, "devanmejia")
        assertEquals("Changed title", changedTask.content.title)
        assertEquals("Changed text", changedTask.content.text)
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(1, "devanmejia")
        Mockito.verify(taskRepository, Mockito.times(1))
            .save(changedTask)
    }

    @Test
    fun `throws exception while changing if task has already been closed`() {
        val newContent = Content("Changed title", "Changed text")
        assertThrows(IllegalArgumentException::class.java) {
            taskService.changeContent(2, newContent, "devanmejia")
        }
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(2, "devanmejia")
    }

    @Test
    fun `throws exception while changing if there is not task with necessary id`() {
        val newContent = Content("Changed title", "Changed text")
        assertThrows(NotPermittedException::class.java) {
            taskService.changeContent(3, newContent, "devanmejia")
        }
        Mockito.verify(taskRepository, Mockito.times(1))
            .findByIdAndUserLogin(3, "devanmejia")
    }
}
