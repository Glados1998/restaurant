package com.wac.my_restuarant.Menu

import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Dish.DishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/menus")
class MenuController(
    private val menuService: MenuService,
    private val dishService: DishService
) {

    @GetMapping("/add")
    fun addMenuForm(model: Model): String {
        model.addAttribute("menu", Menu())
        return "admin/add-edit-menu"
    }

    @PostMapping("/save")
    fun saveMenu(@ModelAttribute menu: Menu): String {
        menuService.save(menu)
        return "redirect:/menus"
    }

    @GetMapping
    fun findAll(): ResponseEntity<Iterable<Menu>> {
        return ResponseEntity.ok(menuService.findAll())
    }

    @GetMapping("/{id}")
    fun showMenu(@PathVariable id: Long, model: Model): String {
        val menu = menuService.findById(id)
        model.addAttribute("menu", menu)
        return "show/show-menu"
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            menuService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody menu: Menu): ResponseEntity<Menu> {
        return try {
            ResponseEntity.ok(menuService.edit(id, menu))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @PostMapping("/menu/{id}/addDish")
    fun addDishToMenu(@PathVariable("id") id: Long, @ModelAttribute dish: Dish, @RequestParam(required=false) addToCardDirectly: Boolean, model: Model): String {
        val menu = menuService.findById(id)
        dish.menu = menu
        if(addToCardDirectly) {
            dish.card = menu.card
        }
        dishService.save(dish)
        return "redirect:/menu/" + id
    }

}
