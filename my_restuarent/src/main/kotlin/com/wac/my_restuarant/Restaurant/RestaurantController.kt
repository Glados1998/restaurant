package com.wac.my_restuarant.Restaurant

import com.wac.my_restuarant.Review.ReviewService
import jakarta.servlet.http.HttpSession
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
import java.util.*
import kotlin.NoSuchElementException

@Controller
@RequestMapping("/restaurant")
class RestaurantController(
    private val restaurantService: RestaurantService,
    private val restaurantRepository: RestaurantRepository,
    private val reviewService: ReviewService,
) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("reviews", reviewService.findTop4ByOrderByCreatedAtDesc())
        return "index" // the name of the Thymeleaf template
    }

    @GetMapping("/card")
    fun card(model: Model): String {
        return "selection" // the name of the Thymeleaf template
    }

    @GetMapping("/about")
    fun contact(): String {
        return "about" // the name of the Thymeleaf template
    }

    @GetMapping("/login-page")
    fun login(): String {
        return "login" // the name of the Thymeleaf template
    }

    @PostMapping("/login")
    fun login(
        @RequestParam("name") name: String,
        @RequestParam("password") password: String,
        model: Model,
        session: HttpSession
    ): String {
        val isValidAdmin = restaurantService.authenticateAdmin(name, password)
        return if (isValidAdmin) {
            session.setAttribute("admin", name) // This sets the admin attribute in the session after successful login
            "redirect:/restaurant/settings"
        } else {
            model.addAttribute("error", "Invalid name or password.")
            "redirect:/restaurant/login-page"
        }
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/restaurant/login-page"
    }

    @GetMapping("/restaurant-dashboard")
    fun dashboard(session: HttpSession): String {
        val admin = session.getAttribute("admin")
        return if (admin == null) {
            "redirect:/restaurant/login-page" // If the admin attribute is not in the session, redirect to the login page
        } else {
            return "/admin/restaurant/settings" // If the admin attribute is in the session, display the dashboard
        }
    }

    @GetMapping("/settings")
    fun settings(model: Model): String {
        val restaurant = restaurantRepository.findById(1).orElse(Restaurant())
        model.addAttribute("restaurant", restaurant)
        return "/admin/restaurant-settings" // the name of the Thymeleaf template
    }

    @PostMapping("/settings/save")
    fun restaurantSettings(
        @ModelAttribute restaurant: Restaurant, bindingResult: BindingResult,
        @RequestParam("headerImage") headerImageFile: MultipartFile, model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            return "admin/restaurant-settings"
        }
        if (!headerImageFile.isEmpty) {
            val filename = "header-image." + FilenameUtils.getExtension(headerImageFile.originalFilename)
            val path =
                Paths.get("/Users/L/Desktop/Web.tmp/school/Wac_semestre_4/projet_en_solo/W-WEB-842-MLH-4-1-java-jerome-alexandre.greder/my_restuarent/src/main/resources/static/images/front/$filename")
            restaurant.imagePath = filename
            try {
                Files.write(path, headerImageFile.bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
            } catch (e: IOException) {
                e.printStackTrace()
                println("Error writing file: " + e.message)
            }
        }

        val existingRestaurant = restaurantService.getRestaurant()
        if (existingRestaurant != null) {
            existingRestaurant.name = restaurant.name
            existingRestaurant.url = restaurant.url
            existingRestaurant.streetAddress = restaurant.streetAddress
            existingRestaurant.streetNumber = restaurant.streetNumber
            existingRestaurant.city = restaurant.city
            existingRestaurant.postalCode = restaurant.postalCode
            existingRestaurant.email = restaurant.email
            existingRestaurant.phone = restaurant.phone
            existingRestaurant.mainColor = restaurant.mainColor
            existingRestaurant.secondaryColor = restaurant.secondaryColor
            existingRestaurant.fontColor = restaurant.fontColor
            existingRestaurant.linkColor = restaurant.linkColor
            existingRestaurant.description = restaurant.description
            existingRestaurant.imagePath = restaurant.imagePath
            restaurantService.save(existingRestaurant)
        } else {
            if (!headerImageFile.isEmpty) {
                val filename = "header-image." + FilenameUtils.getExtension(headerImageFile.originalFilename)
                val path =
                    Paths.get("/Users/L/Desktop/Web.tmp/school/Wac_semestre_4/projet_en_solo/W-WEB-842-MLH-4-1-java-jerome-alexandre.greder/my_restuarent/src/main/resources/static/images/front/$filename")
                restaurant.imagePath = filename
                try {
                    Files.write(path, headerImageFile.bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
                } catch (e: IOException) {
                    e.printStackTrace()
                    println("Error writing file: " + e.message)
                }
            }
            restaurantService.save(restaurant)
        }

        return "redirect:/restaurant/settings"
    }
    @PostMapping("/reset")
    fun resetById(): String {
        try {
            val restaurant = restaurantService.findById(1)
            restaurant.name = ""
            restaurant.url = ""
            restaurant.streetAddress = ""
            restaurant.streetNumber = ""
            restaurant.city = ""
            restaurant.postalCode = ""
            restaurant.email = ""
            restaurant.phone = ""
            restaurant.mainColor = ""
            restaurant.secondaryColor = ""
            restaurant.fontColor = ""
            restaurant.linkColor = ""
            restaurant.imagePath = ""
            restaurant.description = ""

            // Save the updated restaurant back to the database.
            restaurantService.save(restaurant)
        } catch (e: NoSuchElementException) {
            println("No such restaurant.")
            return "redirect:/restaurant/settings"
        }
        return "redirect:/restaurant/settings"
    }

}

