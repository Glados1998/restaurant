package Restaurant

import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : org.springframework.data.repository.CrudRepository<Restaurant, Long> {}