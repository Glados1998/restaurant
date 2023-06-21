package com.wac.my_restuarant.Persona

import org.springframework.stereotype.Repository

@Repository
interface PersonaRepository : org.springframework.data.repository.CrudRepository<Review, Long> {
}