
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface DishRepository : JpaRepository<Dish, Long> {

}