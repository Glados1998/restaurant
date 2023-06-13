package Dish

import org.springframework.stereotype.Repository

@Repository
interface DishRepository : org.springframework.data.repository.CrudRepository<Dish, Long> {}