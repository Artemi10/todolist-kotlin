package devanmejia.todolist.transfer

import java.util.*

data class CreatedTaskDTO(
    var tittle: String,
    var description: String)

data class UpdatedTaskDTO(
    var tittle: String,
    var description: String,
    var date: Date
)
