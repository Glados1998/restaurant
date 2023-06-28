package com.wac.my_restuarant.Review

import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun findAll(): Iterable<Review> = reviewRepository.findAll()

    fun findById(id: Long): Review = reviewRepository.findById(id).orElseThrow()

    fun save(review: Review): Review = reviewRepository.save(review)

    fun deleteById(id: Long) = reviewRepository.deleteById(id)
}