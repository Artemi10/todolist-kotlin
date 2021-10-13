package devanmejia.todolist.service.task

import devanmejia.todolist.exception.NotPermittedException
import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import devanmejia.todolist.repository.TaskRepository
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository) : TaskService {

    override fun getAllUsersTasks(login: String) =
        taskRepository.findAllByUserLogin(login)

    override fun createNewTask(taskDTO: TaskDTO, user: User): Task {
        val newTask = Task(taskDTO.content, taskDTO.isReady, taskDTO.date, user)
        return taskRepository.save(newTask)
    }

    override fun removeTask(taskId: Long, login: String) =
        taskRepository.deleteByIdAndUserLogin(taskId, login)

    override fun closeTask(taskId: Long, login: String): Task {
        val task = taskRepository.findByIdAndUserLogin(taskId, login)
            ?: throw NotPermittedException("Can not close task $taskId")
        task.isReady = true
        return taskRepository.save(task)
    }

    override fun changeContent(taskId: Long, content: Content, login: String): Task {
        val task = taskRepository.findByIdAndUserLogin(taskId, login)
            ?: throw NotPermittedException("Can not change task $taskId content")
        if (!task.isReady){
            task.content = content
            return taskRepository.save(task)
        }
        else throw IllegalArgumentException("Task $taskId has already been closed")
    }
}
