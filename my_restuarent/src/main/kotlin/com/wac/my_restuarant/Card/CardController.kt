package com.wac.my_restuarant.Card

import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Dish.DishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/cards")
class CardController(
    private val cardService: CardService,
    private val dishService: DishService
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

    @PostMapping("/card/{id}/addDish")
    fun addDishToCard(@PathVariable("id") id: Long, @ModelAttribute dish: Dish, model: Model): String {
        val card = cardService.findById(id)
        dish.card = card
        dishService.save(dish)
        return "redirect:/card/" + id
    }
}
