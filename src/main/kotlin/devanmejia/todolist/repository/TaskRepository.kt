package devanmejia.todolist.repository

import devanmejia.todolist.model.Task
import devanmejia.todolist.transfer.Period
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : MongoRepository<Task, String>{
    fun findAllByDateBetween(date1: Date, date2: Date): List<Task>
}
