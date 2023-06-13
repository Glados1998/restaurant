package Menu

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class MenuController(private val menuService: MenuService) {

    @GetMapping
    fun findAll(): ResponseEntity<Iterable<Menu>> {
        return ResponseEntity.ok(menuService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Menu> {
        return try {
            ResponseEntity.ok(menuService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun create(@RequestBody menu: Menu): ResponseEntity<Menu> {
        return ResponseEntity.ok(menuService.save(menu))
    }

    @DeleteMapping("/{id}/delete")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            menuService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @RequestBody menu: Menu): ResponseEntity<Menu> {
        return try {
            ResponseEntity.ok(menuService.edit(id, menu))
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
