package com.wac.my_restuarant.Persona

import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : org.springframework.data.repository.CrudRepository<Review, Long> {
    fun findByName(name: String): Review?
}