package com.wac.my_restuarant.Allergies

import org.springframework.stereotype.Repository

@Repository
interface AllergiesRepository : org.springframework.data.repository.CrudRepository<Allergy, Long> {

}