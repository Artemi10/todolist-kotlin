package devanmejia.todolist.transfer

import devanmejia.todolist.model.Content

data class TaskDTO(
    val content: Content
)

data class UserDTO(
    val login: String
)
