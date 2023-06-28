package com.wac.my_restuarant.Dish

import com.wac.my_restuarant.Review.Review
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
        model.addAttribute("review", Review())
        return "show/show-dish"
    }


    @PostMapping("/save")
    fun create(
        @ModelAttribute dish: Dish,
        bindingResult: BindingResult,
        @RequestParam("image") dishImageFile: MultipartFile
    ): String {
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
