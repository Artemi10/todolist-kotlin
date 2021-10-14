package devanmejia.todolist.model

import org.hibernate.Hibernate
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
) : BaseEntity(){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Task
        return content == other.content && isReady == other.isReady
                && date == other.date && user == other.user
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + isReady.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

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

@Entity
@Table(name = "users")
data class User(
    var login: String,
    var birthDate: Date,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var tasks: MutableList<Task>
) : BaseEntity(){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User
        return login == other.login && birthDate == other.birthDate
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + birthDate.hashCode()
        return result
    }

    override fun toString(): String {
        return this::class.simpleName + "(login = $login , birthDate = $birthDate )"
    }
}
