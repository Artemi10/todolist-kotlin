package devanmejia.todolist.repository

import devanmejia.todolist.model.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long?> {
    fun findAllByUserLogin(login: String): List<Task>
    fun findByIdAndUserLogin(id: Long, login: String): Task?
    fun deleteByIdAndUserLogin(id: Long, login: String)
}
