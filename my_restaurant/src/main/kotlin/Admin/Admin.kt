import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity
data class Admin(
        @jakarta.persistence.Id @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,

        val name: String = "",

        @Column(name = "password_hash")
        val passwordHash: String = "",

        val email: String = ""
)