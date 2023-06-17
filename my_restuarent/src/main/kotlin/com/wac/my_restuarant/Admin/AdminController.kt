package com.wac.my_restuarant.Admin

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class AdminController(private val adminService: AdminService) {

    @GetMapping
    fun findAll(): ResponseEntity<Iterable<Admin>> {
        return ResponseEntity.ok(adminService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Admin> {
        return try {
            ResponseEntity.ok(adminService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun create(@RequestBody admin: Admin): ResponseEntity<Admin> {
        return ResponseEntity.ok(adminService.save(admin))
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            adminService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody admin: Admin): ResponseEntity<Admin> {
        return try {
            ResponseEntity.ok(adminService.edit(id, admin))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
