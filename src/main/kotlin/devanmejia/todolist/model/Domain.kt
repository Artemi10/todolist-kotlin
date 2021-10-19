package devanmejia.todolist.model

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import java.util.*
import javax.persistence.*

@MappedSuperclass
open class GeneratedIdEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @NaturalId
    val uuid: UUID = UUID.randomUUID()
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
) : GeneratedIdEntity(){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Task
        return uuid == other.uuid
    }

    override fun hashCode() = uuid.hashCode()

    override fun toString(): String {
        return this::class.simpleName + "(content = $content , " +
                "isReady = $isReady , date = $date ,user = $user )"
    }
}

@Embeddable
data class Content(
    var title: String,
    var text: String = ""
)

@MappedSuperclass
open class BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

@Entity
@Table(name = "users")
data class User(
    @NaturalId
    val login: String,
    var birthDate: Date,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var tasks: MutableList<Task>
) : BaseEntity(){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User
        return login == other.login
    }

    override fun hashCode() = login.hashCode()

    override fun toString(): String {
        return this::class.simpleName + "(login = $login , birthDate = $birthDate )"
    }
}
