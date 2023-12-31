package com.wac.my_restuarant.Menu

import org.springframework.stereotype.Service

@Service
class MenuService(private val menuRepository: MenuRepository) {

    fun findAll(): Iterable<Menu> = menuRepository.findAll()

    fun findById(id: Long): Menu = menuRepository.findById(id).orElseThrow()

    fun save(menu: Menu): Menu = menuRepository.save(menu)

    fun deleteById(id: Long) = menuRepository.deleteById(id)

    fun edit (id: Long, menu: Menu): Menu {
        val menuToEdit = findById(id)
        menuToEdit.name = menu.name
        return save(menuToEdit)
    }
}