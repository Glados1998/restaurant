package com.wac.my_restuarant.Admin

import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : org.springframework.data.repository.CrudRepository<Admin, Long> {
    fun findByName(name: String): Admin?
}