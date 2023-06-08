package Restaurant

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
public interface RestaurantRepository : JpaRepository<Restaurant, Long> {

}