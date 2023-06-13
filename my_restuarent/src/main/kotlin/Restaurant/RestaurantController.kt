package Restaurant

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/restaurant")
class RestaurantController(private val restaurantService: RestaurantService) {

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("restaurants", restaurantService.findAll())
        return "index" // the name of the Thymeleaf template
    }

    @GetMapping("/card")
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

    @GetMapping("/login")
    fun login(): String {
        return "login" // the name of the Thymeleaf template
    }
    @GetMapping
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

    @PostMapping
    fun create(@RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        return ResponseEntity.ok(restaurantService.save(restaurant))
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
