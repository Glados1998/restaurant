import Card.Card
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface CardRepository : JpaRepository<Card, Long> {

}