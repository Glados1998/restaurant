package com.wac.my_restuarant.Persona

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/personas")
class PersonaController(private val reviewService: ReviewService) {

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

    @PostMapping
    fun create(@RequestBody review: Review): ResponseEntity<Review> {
        return ResponseEntity.ok(reviewService.save(review))
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

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody review: Review): ResponseEntity<Review> {
        return try {
            ResponseEntity.ok(reviewService.edit(id, review))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
