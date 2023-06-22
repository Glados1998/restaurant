package com.wac.my_restuarant.Persona

import org.springframework.stereotype.Service

@Service
class PersonaService(private val personaRepository: PersonaRepository) {

    fun findAll(): Iterable<Persona> = personaRepository.findAll()

    fun findById(id: Long): Persona = personaRepository.findById(id).orElseThrow()

    fun save(Persona: Persona): Persona = personaRepository.save(Persona)

    fun deleteById(id: Long) = personaRepository.deleteById(id)

    fun edit (id: Long, Persona: Persona): Persona {
        val personaToEdit = findById(id)
        personaToEdit.mainColor = Persona.mainColor
        personaToEdit.secondaryColor = Persona.secondaryColor
        personaToEdit.linkColor = Persona.linkColor
        personaToEdit.headerImage = Persona.headerImage
        return save(personaToEdit)
    }
}