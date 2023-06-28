package com.wac.my_restuarant.Review

import com.wac.my_restuarant.Dish.DishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService,
    private val dishService: DishService
) {

    @GetMapping("/all")
    fun findAll(): ResponseEntity<Iterable<Review>> {
        return ResponseEntity.ok(reviewService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Review> {
        return try {
            ResponseEntity.ok(reviewService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/save")
    fun saveReview(
        @ModelAttribute review: Review,
        bindingResult: BindingResult,
        @RequestParam("dishId") dishId: Long
    ): String {
        val dish = dishService.findById(dishId)
        review.dish = dish
        review.createdAt = LocalDate.now().toString()
        reviewService.save(review)

        return "redirect:/dishes/${dish.id}"
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            reviewService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
