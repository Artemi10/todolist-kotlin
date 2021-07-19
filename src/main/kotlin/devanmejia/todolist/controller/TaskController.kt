package devanmejia.todolist.controller

import devanmejia.todolist.model.Task
import devanmejia.todolist.repository.TaskRepository
import devanmejia.todolist.transfer.CreatedTaskDTO
import devanmejia.todolist.transfer.Period
import devanmejia.todolist.transfer.UpdatedTaskDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException



@RestController
@RequestMapping("/task")
class TaskController {
    @Autowired
    private lateinit var taskRepository: TaskRepository;

    @PostMapping
    fun createTask(@RequestBody taskDTO: CreatedTaskDTO): Task{
        return taskRepository.save(Task(taskDTO))
    }

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: String): Task{
        return taskRepository.findById(id)
            .orElseThrow { IllegalArgumentException("No task with id $id") }
    }

    @PostMapping("/date")
    fun getTasksByPeriod(@RequestBody period: Period): List<Task>?{
        if (period.isPeriodValid()){
            return taskRepository.findAllByDateBetween(period.firstDate, period.lastDate)
        } else {
            throw IllegalArgumentException("Period is invalid")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String) {
        taskRepository.deleteById(id)
    }

    @PatchMapping("/{id}")
    fun updateTask(@PathVariable id: String, @RequestBody taskDTO: UpdatedTaskDTO): Task{
        val task: Task = taskRepository.findById(id)
            .orElseThrow { IllegalArgumentException("No task with id $id") }
        task.updateTask(taskDTO)
        return taskRepository.save(task)
    }

}
