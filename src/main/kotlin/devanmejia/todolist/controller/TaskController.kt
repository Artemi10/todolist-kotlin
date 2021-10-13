package devanmejia.todolist.controller

import devanmejia.todolist.model.Content
import devanmejia.todolist.service.task.TaskService
import devanmejia.todolist.service.token.TokenService
import devanmejia.todolist.service.user.UserService
import devanmejia.todolist.transfer.TaskDTO
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class TaskController(
    private val tokenService: TokenService,
    private val taskService: TaskService,
    private val userService: UserService) {

    @GetMapping("/tasks")
    fun getAllUsersTasks(request: HttpServletRequest): List<TaskDTO>{
        val login = tokenService.extractUserLogin(request)
        return TaskDTO.from(taskService.getAllUsersTasks(login))
    }

    @PostMapping("/task")
    fun createNewTask(@RequestBody taskDTO: TaskDTO, request: HttpServletRequest): TaskDTO{
        val login = tokenService.extractUserLogin(request)
        val user = userService.getUserByLogin(login)
        return TaskDTO.from(taskService.createNewTask(taskDTO, user))
    }

    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable("id") taskId: Long, request: HttpServletRequest){
        val login = tokenService.extractUserLogin(request)
        taskService.removeTask(taskId, login)
    }

    @PatchMapping("/task/{id}/close")
    fun closeTask(@PathVariable("id") taskId: Long, request: HttpServletRequest): TaskDTO{
        val login = tokenService.extractUserLogin(request)
        return TaskDTO.from(taskService.closeTask(taskId, login))
    }

    @PatchMapping("/task/{id}/content")
    fun changeContent(@PathVariable("id") taskId: Long,
                      @RequestBody content: Content, request: HttpServletRequest): TaskDTO{
        val login = tokenService.extractUserLogin(request)
        return TaskDTO.from(taskService.changeContent(taskId, content, login))
    }

}
