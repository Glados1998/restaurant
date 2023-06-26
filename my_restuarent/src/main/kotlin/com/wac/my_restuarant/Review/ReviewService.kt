package com.wac.my_restuarant.Review

import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun findAll(): Iterable<Review> = reviewRepository.findAll()

    fun findById(id: Long): Review = reviewRepository.findById(id).orElseThrow()

    fun save(review: Review): Review = reviewRepository.save(review)

    fun deleteById(id: Long) = reviewRepository.deleteById(id)

    fun edit (id: Long, review: Review): Review {
        val reviewToEdit = findById(id)
        reviewToEdit.authorFirstName = review.authorFirstName
        reviewToEdit.authorLastName = review.authorLastName
//        reviewToEdit.rating = review.rating
        reviewToEdit.comment = review.comment
        reviewToEdit.createdAt = review.createdAt
        reviewToEdit.updatedAt = review.updatedAt
        return save(reviewToEdit)
    }
}