package devanmejia.todolist.controller

import devanmejia.todolist.model.Content
import devanmejia.todolist.service.task.TaskService
import devanmejia.todolist.service.user.UserService
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TaskController(
    private val taskService: TaskService,
    private val userService: UserService) {

    @GetMapping("/tasks/user/{login}")
    fun getAllUsersTasks(@PathVariable login: String) =
        TaskDTO.from(taskService.getAllUsersTasks(login))

    @PostMapping("/task/user/{login}")
    fun createNewTask(@PathVariable login: String, @RequestBody taskDTO: TaskDTO): TaskDTO{
        val user = userService.getUserByLogin(login)
        return TaskDTO.from(taskService.createNewTask(taskDTO, user))
    }

    @DeleteMapping("/task/{id}/user/{login}")
    fun deleteTask(@PathVariable("id") taskId: Long, @PathVariable login: String) =
        taskService.removeTask(taskId, login)

    @PatchMapping("/task/{id}/close/user/{login}")
    fun closeTask(@PathVariable("id") taskId: Long, @PathVariable login: String) =
        TaskDTO.from(taskService.closeTask(taskId, login))

    @PatchMapping("/task/{id}/content/user/{login}")
    fun changeContent(@PathVariable("id") taskId: Long,
                      @PathVariable login: String, @RequestBody content: Content) =
        TaskDTO.from(taskService.changeContent(taskId, content, login))

}
