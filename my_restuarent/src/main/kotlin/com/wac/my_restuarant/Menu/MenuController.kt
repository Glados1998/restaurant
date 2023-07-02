package com.wac.my_restuarant.Menu

import com.wac.my_restuarant.Allergies.AllergiesService
import com.wac.my_restuarant.Dish.Dish
import com.wac.my_restuarant.Dish.DishService
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
@RequestMapping("/menus")
class MenuController(
    private val menuService: MenuService,
    private val dishService: DishService,
    private val allergiesService: AllergiesService
) {

    @GetMapping("/add")
    fun addMenuForm(model: Model): String {
        model.addAttribute("menu", Menu())
        return "admin/add-edit-menu"
    }

    @PostMapping("/save")
    fun saveMenu(@ModelAttribute menu: Menu): String {
        menuService.save(menu)
        return "redirect:/menus/${menu.id}"
    }

    @GetMapping("/all")
    fun findAll(model: Model): String {
        val menus = menuService.findAll()
        model.addAttribute("menus", menus)
        return "list/menu-list"
    }

    @GetMapping("/{id}")
    fun showMenu(@PathVariable id: Long, model: Model): String {
        val menu = menuService.findById(id)
        model.addAttribute("menu", menu)
        return "show/show-menu"
    }

    @PostMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Long, model: Model?): String? {
        menuService.deleteById(id)
        return "redirect:/menus/all"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val menu = menuService.findById(id)
        model.addAttribute("menu", menu)
        return "admin/add-edit-menu"
    }

    @GetMapping("/{id}/addDish")
    fun addDishForm(@PathVariable id: Long, model: Model): String {
        val menu = menuService.findById(id)
        val allAllergies = allergiesService.findAll()
        model.addAttribute("menu", menu)
        model.addAttribute("dish", Dish())
        model.addAttribute("allAllergies", allAllergies)
        return "admin/add-dish-to-menu-form"
    }

    @PostMapping("/addDishToMenu")
    fun addDishToMenu(
        @ModelAttribute dish: Dish, bindingResult: BindingResult,
        @RequestParam("image") dishImageFile: MultipartFile, @RequestParam menuId: Long
    ): String {
        val menu = menuService.findById(menuId)
        dish.menu = menu
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
        dishService.save(dish)
        return "redirect:/dishes/${dish.id}"
    }

}
