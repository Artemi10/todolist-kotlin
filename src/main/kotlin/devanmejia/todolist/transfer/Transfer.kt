package devanmejia.todolist.transfer

import devanmejia.todolist.model.Content
import java.util.*

data class TaskDTO(
    val id: Long? = null,
    val content: Content,
    val isReady: Boolean = false,
    val date: Date = Date(),
    val login: String
)

data class UserDTO(
    val id: Long? = null,
    val login: String,
    val birthDate: Date
)
