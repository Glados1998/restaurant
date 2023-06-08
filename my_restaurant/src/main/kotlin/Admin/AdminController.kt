import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminController(private val adminService: AdminService) {

    @GetMapping("/list")
    fun list(model: Model): String {
        model.addAttribute("admins", adminService.findAll())
        return "list"
    }

    @GetMapping("/{id}")
    fun getAdmin(model: Model, @PathVariable id: Long): String {
        model.addAttribute("admin", adminService.findById(id))
        return "admin"
    }

    @PostMapping("/add")
    fun add(admin: Admin): String {
        adminService.save(admin)
        return "redirect:list"
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, admin: Admin): String {
        adminService.update(id, admin)
        return "redirect:../list"
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        adminService.deleteById(id)
        return "redirect:../list"
    }

}