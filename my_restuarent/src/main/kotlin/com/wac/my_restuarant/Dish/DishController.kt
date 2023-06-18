package com.wac.my_restuarant.Dish

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dishes")
class DishController(private val dishService: DishService) {

    @GetMapping("/add")
    fun addDishForm(model: Model): String {
        model.addAttribute("dish", Dish())
        return "add-edit-dishes"
    }

    @GetMapping("/{id}/edit")
    fun editDishForm(@PathVariable id: Long, model: Model): String {
        val dish = dishService.findById(id)
        model.addAttribute("dish", dish)
        return "add-edit-dishes"
    }

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

    @PostMapping("/save")
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

    @PutMapping("/{id}/save/edit")
    fun edit(@PathVariable id: Long, @RequestBody dish: Dish): ResponseEntity<Dish> {
        return try {
            ResponseEntity.ok(dishService.edit(id, dish))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
