package com.wac.my_restuarant.Review

import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : org.springframework.data.repository.CrudRepository<Review, Long> {
    fun findTop4ByOrderByCreatedAtDesc(): List<Review>

}