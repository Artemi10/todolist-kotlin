package devanmejia.todolist.model

import java.util.*
import javax.persistence.*

@MappedSuperclass
open class BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

@Entity
@Table(name = "tasks")
data class Task(
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "title", column = Column(name = "title")),
        AttributeOverride(name = "text", column = Column(name = "text"))
    )
    var content: Content,
    var isReady: Boolean = false,
    var date: Date = Date(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
) : BaseEntity()

@Embeddable
data class Content(
    var title: String,
    var text: String = ""
)

@Entity
@Table(name = "users")
data class User(
    var login: String,
    var birthDate: Date,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var tasks: MutableList<Task>
) : BaseEntity()
