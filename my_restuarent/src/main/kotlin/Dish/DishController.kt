package Dish

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class DishController(private val dishService: DishService) {

    @GetMapping
    fun findAll(): ResponseEntity<Iterable<Dish>> {
        return ResponseEntity.ok(dishService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Dish> {
        return try {
            ResponseEntity.ok(dishService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun create(@RequestBody dish: Dish): ResponseEntity<Dish> {
        return ResponseEntity.ok(dishService.save(dish))
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            dishService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody dish: Dish): ResponseEntity<Dish> {
        return try {
            ResponseEntity.ok(dishService.edit(id, dish))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
