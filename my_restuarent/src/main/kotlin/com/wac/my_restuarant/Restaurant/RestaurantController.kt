package com.wac.my_restuarant.Restaurant

import com.wac.my_restuarant.Persona.PersonaRepository
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/restaurant")
class RestaurantController(
    private val restaurantService: RestaurantService,
    private val restaurantRepository: RestaurantRepository,
    private val personaRepository: PersonaRepository
) {

    @GetMapping("/")
    fun index(model: Model): String {
        val restaurant = restaurantService.getRestaurant()
        model.addAttribute("restaurant", restaurant)
        return "index" // the name of the Thymeleaf template
    }

    @GetMapping("/card/{id}")
    fun card(@PathVariable id: Long, model: Model): String {
        val restaurant = restaurantService.findById(id)
        model.addAttribute("restaurant", restaurant)
        // Assuming you have methods in your service to fetch the card, dishes, and menus
        model.addAttribute("card", restaurantService.findCard())
        model.addAttribute("menus", restaurantService.findAllMenus())
        model.addAttribute("dishes", restaurantService.findAllDishes())
        return "card" // the name of the Thymeleaf template
    }

    @GetMapping("/contact")
    fun contact(): String {
        return "contact" // the name of the Thymeleaf template
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
            "redirect:/restaurant/restaurant-dashboard"
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
            return "/admin/restaurant-dashboard" // If the admin attribute is in the session, display the dashboard
        }
    }

    @GetMapping("/settings")
    fun settings(model: Model): String {
        val restaurant = restaurantRepository.findById(1).orElse(Restaurant())
        model.addAttribute("restaurant", restaurant)
        return "/admin/restaurant-settings" // the name of the Thymeleaf template
    }

    @PostMapping("/settings/save")
    fun restaurantSettings(@ModelAttribute restaurant: Restaurant, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("personas", personaRepository.findAll())
            return "admin/restaurant-settings"
        }

        if (restaurant.persona?.id != null) {
            val persona = personaRepository.findById(restaurant.persona!!.id)
            if (persona.isPresent) {
                restaurant.persona = persona.get()
            } else {
                // handle the error when the persona is not found
                model.addAttribute("personas", personaRepository.findAll())
                model.addAttribute("error", "Persona not found.")
                return "admin/restaurant-settings"
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
            existingRestaurant.persona = restaurant.persona
            restaurantService.save(existingRestaurant)
        } else {
            restaurantService.save(restaurant)
        }

        return "redirect:/restaurant/restaurant-dashboard" // you can redirect to wherever you want upon successful save
    }


    @GetMapping("/all")
    fun findAll(): ResponseEntity<Iterable<Restaurant>> {
        return ResponseEntity.ok(restaurantService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Restaurant> {
        return try {
            ResponseEntity.ok(restaurantService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            restaurantService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        return try {
            ResponseEntity.ok(restaurantService.edit(id, restaurant))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
