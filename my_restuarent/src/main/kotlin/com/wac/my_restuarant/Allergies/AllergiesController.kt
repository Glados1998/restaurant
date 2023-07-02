package com.wac.my_restuarant.Allergies

import org.springframework.ui.Model
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/allergies")
class AllergiesController(
    private val allergiesService: AllergiesService
) {

    @GetMapping("/all")
    fun findAll(model: Model): String {
        val allergies = allergiesService.findAll()
        model.addAttribute("allergies", allergies)
        return "list/allergies-list"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long, model: Model): String {
        val allergy = allergiesService.findById(id)
        model.addAttribute("allergies", allergy)
        return "show/show-allergies"
    }

    @GetMapping("/new")
    fun newReview(model: Model): String {
        model.addAttribute("allergies", Allergy())
        return "admin/add-edit-allergies"
    }

    @PostMapping("/save")
    fun saveReview(
        @ModelAttribute allergies: Allergy,
        bindingResult: BindingResult,
    ): String {
        allergiesService.save(allergies)
        return "redirect:/allergies/${allergies.id}"
    }

    @GetMapping("/edit/{id}")
    fun editReview(@PathVariable id: Long, model: Model): String {
        val allergy = allergiesService.findById(id)
        model.addAttribute("allergies", allergy)
        return "admin/add-edit-allergies"
    }

    @PostMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Long, model: Model?): String? {
        allergiesService.deleteById(id)
        return "redirect:/allergies/all"
    }
}
