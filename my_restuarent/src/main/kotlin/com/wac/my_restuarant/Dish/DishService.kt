package com.wac.my_restuarant.Dish

import com.wac.my_restuarant.Allergies.AllergiesRepository
import com.wac.my_restuarant.Card.CardRepository
import com.wac.my_restuarant.Menu.MenuRepository
import org.springframework.stereotype.Service

@Service
class DishService(
    private val dishRepository: DishRepository,
    private val menuRepository: MenuRepository,
    private val cardRepository: CardRepository,
    private val allergiesRepository: AllergiesRepository
) {

    fun findAll(): Iterable<Dish> = dishRepository.findAll()

    fun findById(id: Long): Dish = dishRepository.findById(id).orElseThrow()

    fun save(dish: Dish): Dish = dishRepository.save(dish)

    fun deleteById(id: Long) = dishRepository.deleteById(id)

    fun edit (id: Long, dish: Dish): Dish {
        val dishToEdit = findById(id)
        dishToEdit.name = dish.name
        dishToEdit.price= dish.price
        dishToEdit.description= dish.description
        dishToEdit.image= dish.image
        return save(dishToEdit)
    }

    fun saveDishAndRelatedEntities(dish: Dish, menuId: Long?, cardId: Long?, allergyIds: List<Long>) {
        val allergies = allergiesRepository.findAllById(allergyIds).toSet()
        val menu = menuId?.let { menuRepository.findById(it).orElse(null) }
        val card = cardId?.let { cardRepository.findById(it).orElse(null) }

        dish.menu = menu
        dish.card = card
        dish.allergies = allergies
        dishRepository.save(dish)
    }

    fun get(id: Long): Dish? {
        return dishRepository.findById(id).orElse(null)
    }

}