package devanmejia.todolist.transfer

import devanmejia.todolist.model.Content
import devanmejia.todolist.model.Task
import devanmejia.todolist.model.User
import java.util.*

data class TaskDTO(
    val id: Long? = null,
    val content: Content,
    val isReady: Boolean = false,
    val date: Date = Date()
){
    companion object{
        fun from(tasks: List<Task>) = tasks.map { from(it) }.toList()
        fun from(task: Task) = TaskDTO(task.id, task.content, task.isReady, task.date)
    }
}

data class UserDTO(
    val id: Long? = null,
    val login: String,
    val birthDate: Date
){
    companion object{
        fun from(users: List<User>) = users.map { from(it) }.toList()
        fun from(user: User) = UserDTO(user.id, user.login, user.birthDate)
    }
}
