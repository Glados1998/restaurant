package com.wac.my_restuarant.Dish

import com.wac.my_restuarant.Allergies.AllergiesService
import com.wac.my_restuarant.Card.CardService
import com.wac.my_restuarant.Menu.MenuService
import com.wac.my_restuarant.Review.Review
import org.apache.commons.io.FilenameUtils
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
@RequestMapping("/dishes")
class DishController(
    private val dishService: DishService,
    private val menuService: MenuService,
    private val cardService: CardService,
    private val allergiesService: AllergiesService
) {

    @GetMapping("/add")
    fun addDishForm(model: Model): String {
        val allMenus = menuService.findAll()
        val allCards = cardService.findAll()
        val allAllergies = allergiesService.findAll()
        model.addAttribute("dish", Dish())
        model.addAttribute("menus", allMenus)
        model.addAttribute("cards", allCards)
        model.addAttribute("allAllergies", allAllergies)
        return "admin/add-edit-dish"
    }

    @GetMapping("edit/{id}")
    fun editDishForm(@PathVariable id: Long, model: Model): String {
        val dish = dishService.findById(id)
        val allMenus = menuService.findAll()
        val allCards = cardService.findAll()
        val allAllergies = allergiesService.findAll()
        model.addAttribute("dish", dish)
        model.addAttribute("allAllergies", allAllergies)
        model.addAttribute("menus", allMenus)
        model.addAttribute("cards", allCards)
        model.addAttribute("allAllergies", allAllergies)
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
        model.addAttribute("review", Review())
        return "show/show-dish"
    }


    @PostMapping("/save")
    fun create(
        @ModelAttribute dish: Dish,
        bindingResult: BindingResult,
        @RequestParam("image", required = false) dishImageFile: MultipartFile?,
        @RequestParam("menuId") menuId: Long?,
        @RequestParam("cardId") cardId: Long?,
        @RequestParam("allergies") allergyIds: List<Long>
    ): String {
        // Check if an image file was provided
        if (dishImageFile != null && !dishImageFile.isEmpty) {
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
        } else if (dish.id != 0L) {
            // If no new image was provided and the dish already exists, keep the existing image
            val existingDish = dishService.get(dish.id)
            if (existingDish != null) {
                dish.image = existingDish.image
            }
        }
        dishService.saveDishAndRelatedEntities(dish, menuId, cardId, allergyIds)
        return "redirect:/dishes/${dish.id}"
    }


    @PostMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Long, model: Model?): String? {
        dishService.deleteById(id)
        return "redirect:/dishes/all"
    }

}
