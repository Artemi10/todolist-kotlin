package devanmejia.todolist.service.task

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.stereotype.Service

@Service
interface TaskService {
    fun getAllUsersTasks(login: String): List<Task>
    fun createNewTask(taskDTO: TaskDTO): Task
    fun removeTask(taskId: Long): Task
    fun closeTask(taskId: Long)
    fun changeContent(taskId: Long, content: Content)
}
