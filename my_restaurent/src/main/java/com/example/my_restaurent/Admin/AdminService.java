package com.example.my_restaurent.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addNewAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long adminId) {
        boolean exists = adminRepository.existsById(adminId);
        if (!exists) {
            throw new IllegalStateException("Admin with id " + adminId + " does not exists");
        }
        adminRepository.deleteById(adminId);
    }

    public void updateAdmin(Long adminId, String name, String email, String password) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new IllegalStateException("Admin with id " + adminId + " does not exists"));
        if (name != null && name.length() > 0 && !name.equals(admin.getName())) {
            admin.setName(name);
        }
        if (email != null && email.length() > 0 && !email.equals(admin.getEmail())) {
            admin.setEmail(email);
        }
        if (password != null && password.length() > 0 && !password.equals(admin.getPassword())) {
            admin.setPassword(password);
        }
        adminRepository.save(admin);
    }

    public Admin getAdmin(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new IllegalStateException("Admin with id " + adminId + " does not exists"));
    }

    public void loginAdmin(String email, String password) {
        Admin admin = (Admin) adminRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Admin with email " + email + " does not exists"));
        if (!admin.getPassword().equals(password)) {
            throw new IllegalStateException("Wrong password");
        }
    }

}
