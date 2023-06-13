package Restaurant

import org.springframework.stereotype.Service
import Card.CardRepository
import Menu.MenuRepository
import Dish.DishRepository

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository,
                        private val cardRepository: CardRepository,
                        private val menuRepository: MenuRepository,
                        private val dishRepository: DishRepository) {

    fun findAll(): Iterable<Restaurant> = restaurantRepository.findAll()

    fun findById(id: Long): Restaurant = restaurantRepository.findById(id).orElseThrow()

    fun save(restaurant: Restaurant): Restaurant = restaurantRepository.save(restaurant)

    fun deleteById(id: Long) = restaurantRepository.deleteById(id)

    fun edit (id: Long, restaurant: Restaurant): Restaurant {
        val restaurantToEdit = findById(id)
        restaurantToEdit.name = restaurant.name
        restaurantToEdit.url = restaurant.url
        restaurantToEdit.colorChoice = restaurant.colorChoice
        restaurantToEdit.image = restaurant.image
        return save(restaurantToEdit)
    }

    fun findCard() = cardRepository.findAll().first()

    fun findAllMenus() = menuRepository.findAll()

    fun findAllDishes() = dishRepository.findAll()
}
