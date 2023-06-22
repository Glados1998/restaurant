package com.wac.my_restuarant.Card

import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Dish.DishService
import com.wac.my_restuarant.Menu.Menu
import com.wac.my_restuarant.Menu.MenuService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/cards")
class CardController(
    private val cardService: CardService,
    private val dishService: DishService,
    private val menuService: MenuService
) {

    @GetMapping("/add")
    fun addCardForm(model: Model): String {
        model.addAttribute("card", Card())
        return "admin/add-edit-card"
    }

    @PostMapping("/save")
    fun saveCard(@ModelAttribute card: Card): String {
        cardService.save(card)
        return "redirect:/cards"
    }

    @GetMapping("/all")
    fun findAll(model: Model): String {
        val cards = cardService.findAll()
        model.addAttribute("cards", cards)
        return "list/card-list"
    }

    @GetMapping("/{id}")
    fun showCard(@PathVariable id: Long, model: Model): String {
        val card = cardService.findById(id)
        model.addAttribute("card", card)
        return "show/show-card"
    }


    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            cardService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody card: Card): ResponseEntity<Card> {
        return try {
            ResponseEntity.ok(cardService.edit(id, card))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/{id}/addDish")
    fun addDishForm(@PathVariable id: Long, model: Model): String {
        val card = cardService.findById(id)
        model.addAttribute("card", card)
        model.addAttribute("dish", Dish())
        return "admin/add-dish-to-card-form"
    }

    @PostMapping("/addDishToCard")
    fun addDishToMenu(@ModelAttribute dish: Dish, @RequestParam cardId: Long): String {
        val card = cardService.findById(cardId)
        dish.card = card
        dishService.save(dish)
        return "redirect:/cards/" + cardId
    }

    @GetMapping("/{id}/addMenu")
    fun addMenuForm(@PathVariable id: Long, model: Model): String {
        val card = cardService.findById(id)
        val allMenus = menuService.findAll()
        model.addAttribute("card", card)
        model.addAttribute("menu", Menu())
        model.addAttribute("allMenus", allMenus)
        return "admin/add-menu-to-card"
    }

    @PostMapping("/addMenuToCard")
    fun addMenuToCard(@ModelAttribute menu: Menu, @RequestParam cardId: Long): String {
        val card = cardService.findById(cardId)
        // If the menu ID is not null, it's an existing menu, so find it in the database
        if (menu.id != null) {
            val existingMenu = menuService.findById(menu.id)
            existingMenu.card = card
            menuService.save(existingMenu)
        } else {
            // Otherwise, it's a new menu, so save it with the card
            menu.card = card
            menuService.save(menu)
        }
        return "redirect:/cards/" + cardId
    }

}
