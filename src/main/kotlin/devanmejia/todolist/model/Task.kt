package devanmejia.todolist.model

import devanmejia.todolist.transfer.CreatedTaskDTO
import devanmejia.todolist.transfer.UpdatedTaskDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("tasks")
data class Task(
    @Id var id: String?,
    var tittle: String,
    var description: String,
    var date: Date){

    constructor(taskDTO: CreatedTaskDTO) : this(null, taskDTO.tittle, taskDTO.description, Date())

    fun updateTask(updatedTaskDTO: UpdatedTaskDTO){
        tittle = updatedTaskDTO.tittle
        description = updatedTaskDTO.description
        date = updatedTaskDTO.date
    }

}
