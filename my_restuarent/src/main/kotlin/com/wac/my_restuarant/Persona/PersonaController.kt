package com.wac.my_restuarant.Persona

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/personas")
class PersonaController(
    private val personaService: PersonaService,
    private val personaRepository: PersonaRepository
) {

    @GetMapping("/all")
    fun findAll(): ResponseEntity<Iterable<Persona>> {
        return ResponseEntity.ok(personaService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Persona> {
        return try {
            ResponseEntity.ok(personaService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/add")
    fun addPersonaForm(model: Model): String {
        model.addAttribute("persona", Persona())
        return "admin/add-edit-persona-form"
    }

    @PostMapping("/save")
    fun create(@ModelAttribute persona: Persona, @RequestParam("headerImage") image: MultipartFile): ResponseEntity<Persona> {
        return try {
            persona.headerImage = image.bytes
            val savedPersona = personaRepository.save(persona)
            ResponseEntity.ok(savedPersona)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }


    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            personaService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody persona: Persona): ResponseEntity<Persona> {
        return try {
            ResponseEntity.ok(personaService.edit(id, persona))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
