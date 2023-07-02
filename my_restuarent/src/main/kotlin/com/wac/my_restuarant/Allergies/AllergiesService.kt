package com.wac.my_restuarant.Allergies

import org.springframework.stereotype.Service

@Service
class AllergiesService(private val allergiesRepository: AllergiesRepository) {

    fun findAll(): Iterable<Allergy> = allergiesRepository.findAll()

    fun findById(id: Long): Allergy = allergiesRepository.findById(id).orElseThrow()

    fun save(allergies: Allergy): Allergy = allergiesRepository.save(allergies)

    fun deleteById(id: Long) = allergiesRepository.deleteById(id)
}