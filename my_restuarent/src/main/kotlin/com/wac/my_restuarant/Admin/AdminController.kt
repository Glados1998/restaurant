package com.wac.my_restuarant.Admin

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admins")
class AdminController(private val adminService: AdminService) {

    @GetMapping("/all")
    fun findAll(model: Model): String {
        val admins = adminService.findAll()
        model.addAttribute("admins", admins)
        return "list/admin-list"
    }

    @GetMapping("/add")
    fun addAdmin(model: Model): String {
        model.addAttribute("admin", Admin())
        return "admin/add-edit-admin"
    }

    @PostMapping("/save")
    fun create(@RequestBody admin: Admin): String {
        adminService.save(admin)
        return "redirect:/admins/all"
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable id: Long): String {
        adminService.deleteById(id)
        return "redirect:/admins/all"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val admin = adminService.findById(id)
        model.addAttribute("admin", admin)
        return "admin/add-edit-admin"
    }
}
