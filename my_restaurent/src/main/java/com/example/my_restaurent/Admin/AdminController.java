package com.example.my_restaurent.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/create")
    public String showCreateForm(Admin admin) {
        return "admin-create";
    }

    @PostMapping("/save")
    public String save(Admin admin) {
        adminService.addNewAdmin(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Admin admin = adminService.getAdmin(id);
        model.addAttribute("admin", admin);
        return "update-admin";
    }

    @PostMapping("/{id}/update")
    public String updateAdmin(@PathVariable("id") long id, Admin admin) {
        adminService.updateAdmin(id, admin.getName(), admin.getEmail(), admin.getPassword());
        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable("id") long id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/list";
    }
}
