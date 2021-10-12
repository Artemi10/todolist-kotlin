package devanmejia.todolist.service.task

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.stereotype.Service

@Service
interface TaskService {
    fun getAllUsersTasks(login: String): List<Task>
    fun createNewTask(taskDTO: TaskDTO, user: User): Task
    fun removeTask(taskId: Long, login: String)
    fun closeTask(taskId: Long, login: String): Task
    fun changeContent(taskId: Long, content: Content, login: String): Task
}
