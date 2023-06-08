package Dish

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
public interface DishRepository : JpaRepository<Dish, Long> {

}