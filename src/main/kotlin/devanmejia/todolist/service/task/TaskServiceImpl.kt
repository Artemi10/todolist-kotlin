package devanmejia.todolist.service.task

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.repository.TaskRepository
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository) : TaskService {

    override fun getAllUsersTasks(login: String): List<Task> {
        TODO("Not yet implemented")
    }

    override fun createNewTask(taskDTO: TaskDTO): Task {
        TODO("Not yet implemented")
    }

    override fun removeTask(taskId: Long): Task {
        TODO("Not yet implemented")
    }

    override fun closeTask(taskId: Long) {
        TODO("Not yet implemented")
    }

    override fun changeContent(taskId: Long, content: Content) {
        TODO("Not yet implemented")
    }
}
