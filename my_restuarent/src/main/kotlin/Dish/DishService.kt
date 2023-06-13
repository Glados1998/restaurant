package Dish

import org.springframework.stereotype.Service

@Service
class DishService(private val dishRepository: DishRepository) {

    fun findAll(): Iterable<Dish> = dishRepository.findAll()

    fun findById(id: Long): Dish = dishRepository.findById(id).orElseThrow()

    fun save(dish: Dish): Dish = dishRepository.save(dish)

    fun deleteById(id: Long) = dishRepository.deleteById(id)

    fun edit (id: Long, dish: Dish): Dish {
        val adminToEdit = findById(id)
        adminToEdit.name = dish.name
        adminToEdit.password = dish.password
        return save(adminToEdit)
    }
}