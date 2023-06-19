package com.wac.my_restuarant.Dish

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/dishes")
class DishController(private val dishService: DishService) {

    @GetMapping("/add")
    fun addDishForm(model: Model): String {
        model.addAttribute("dish", Dish())
        return "admin/add-edit-dish"
    }

    @GetMapping("/{id}/edit")
    fun editDishForm(@PathVariable id: Long, model: Model): String {
        val dish = dishService.findById(id)
        model.addAttribute("dish", dish)
        return "admin/add-edit-dish"
    }

    @GetMapping("/all")
    fun findAll(model: Model): String {
        val dishes = dishService.findAll()
        model.addAttribute("dishes", dishes)
        return "list/dish-list"
    }

    @GetMapping("/{id}")
    fun showDish(@PathVariable id: Long, model: Model): String {
        val dish = dishService.findById(id)
        model.addAttribute("dish", dish)
        return "show/show-dish"
    }


    @PostMapping("/save")
    fun create(@ModelAttribute dish: Dish): String {
        dishService.save(dish)
        return "redirect:/admin/restaurant-dashboard"
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
