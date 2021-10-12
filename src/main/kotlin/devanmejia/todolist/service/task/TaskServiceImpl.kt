package devanmejia.todolist.service.task

import devanmejia.todolist.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository) : TaskService {
}
