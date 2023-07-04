package com.wac.my_restuarant.Card

import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Dish.DishService
import com.wac.my_restuarant.Menu.Menu
import com.wac.my_restuarant.Menu.MenuService
import org.apache.commons.io.FilenameUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

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
        return "redirect:/cards/all"
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


    @PostMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Long, model: Model?): String? {
        cardService.deleteById(id)
        return "redirect:/cards/all"
    }

    @GetMapping("/addDish/{id}")
    fun addDishForm(@PathVariable id: Long, model: Model): String {
        val card = cardService.findById(id)
        model.addAttribute("card", card)
        model.addAttribute("dish", Dish())
        return "admin/add-dish-to-card-form"
    }

    @PostMapping("/addDishToCard")
    fun addDishToMenu(
        @ModelAttribute dish: Dish, bindingResult: BindingResult,
        @RequestParam("image") dishImageFile: MultipartFile, @RequestParam cardId: Long
    ): String {
        val card = cardService.findById(cardId)
        if (!dishImageFile.isEmpty) {
            val filename = "${dish.name}-image." + FilenameUtils.getExtension(dishImageFile.originalFilename)
            val path =
                Paths.get("/Users/L/Desktop/Web.tmp/school/Wac_semestre_4/projet_en_solo/W-WEB-842-MLH-4-1-java-jerome-alexandre.greder/my_restuarent/src/main/resources/static/images/common/$filename")
            dish.image = filename
            try {
                Files.write(path, dishImageFile.bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
            } catch (e: IOException) {
                e.printStackTrace()
                println("Error writing file: " + e.message)
            }
        }
        dish.card = card
        dishService.save(dish)
        return "redirect:/cards/" + cardId
    }

    @GetMapping("/addMenu/{id}")
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
