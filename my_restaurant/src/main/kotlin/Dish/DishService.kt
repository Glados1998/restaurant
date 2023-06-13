import org.springframework.stereotype.Service

@Service
class DishService(private val dishRepository: DishRepository) {

    fun findAll(): List<Dish> = dishRepository.findAll()

    fun save(dish: Dish) = dishRepository.save(dish)

    fun findById(id: Long): Dish = dishRepository.findById(id).orElseThrow { DishNotFoundException(id) }

    fun delete(id: Long) = dishRepository.deleteById(id)

    fun update(id: Long, dish: Dish) {
        val dishToUpdate = dishRepository.findById(id).orElseThrow { DishNotFoundException(id) }
        dishToUpdate.name = dish.name
        dishToUpdate.description = dish.description
        dishToUpdate.price = dish.price
        dishToUpdate.image = dish.image
        dishRepository.save(dishToUpdate)
    }

    fun DishNotFoundException(id: Long): Exception {
        return Exception("there has been an error")
    }
}