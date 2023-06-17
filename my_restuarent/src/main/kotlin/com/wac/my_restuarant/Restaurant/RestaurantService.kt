package com.wac.my_restuarant.Restaurant

import com.wac.my_restuarant.Admin.AdminRepository
import org.springframework.stereotype.Service
import com.wac.my_restuarant.Card.CardRepository
import com.wac.my_restuarant.Menu.MenuRepository
import com.wac.my_restuarant.Dish.DishRepository

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val cardRepository: CardRepository,
    private val menuRepository: MenuRepository,
    private val dishRepository: DishRepository,
    private val adminRepository: AdminRepository
) {

    fun findAll(): Iterable<Restaurant> = restaurantRepository.findAll()

    fun findById(id: Long): Restaurant = restaurantRepository.findById(id).orElseThrow()

    fun save(restaurant: Restaurant): Restaurant = restaurantRepository.save(restaurant)

    fun deleteById(id: Long) = restaurantRepository.deleteById(id)

    fun edit(id: Long, restaurant: Restaurant): Restaurant {
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

    fun authenticateAdmin(name: String, password: String): Boolean {
        val admin = adminRepository.findByName(name)
        if (admin != null) {
            return password == admin.password
        }
        return false
    }
}
