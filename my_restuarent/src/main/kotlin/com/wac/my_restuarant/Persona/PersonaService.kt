package com.wac.my_restuarant.Persona

import org.springframework.stereotype.Service

@Service
class PersonaService(private val reviewRepository: ReviewRepository) {

    fun findAll(): Iterable<Review> = reviewRepository.findAll()

    fun findById(id: Long): Review = reviewRepository.findById(id).orElseThrow()

    fun save(review: Review): Review = reviewRepository.save(review)

    fun deleteById(id: Long) = reviewRepository.deleteById(id)

    fun edit (id: Long, review: Review): Review {
        val adminToEdit = findById(id)
        adminToEdit.name = review.name
        adminToEdit.password = review.password
        return save(adminToEdit)
    }
}