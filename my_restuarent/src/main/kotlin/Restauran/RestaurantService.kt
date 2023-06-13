package Restauran

import org.springframework.stereotype.Service

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {

    fun findAll(): Iterable<Restaurant> = restaurantRepository.findAll()

    fun findById(id: Long): Restaurant = restaurantRepository.findById(id).orElseThrow()

    fun save(restaurant: Restaurant): Restaurant = restaurantRepository.save(restaurant)

    fun deleteById(id: Long) = restaurantRepository.deleteById(id)

    fun edit (id: Long, restaurant: Restaurant): Restaurant {
        val adminToEdit = findById(id)
        adminToEdit.name = restaurant.name
        adminToEdit.password = restaurant.password
        return save(adminToEdit)
    }
}