package devanmejia.todolist.repository

import devanmejia.todolist.model.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long?> {
}
