package Admin

import org.springframework.stereotype.Service

@Service
class AdminService(private val adminRepository: AdminRepository) {

    fun findAll(): Iterable<Admin> = adminRepository.findAll()

    fun findById(id: Long): Admin = adminRepository.findById(id).orElseThrow()

    fun save(admin: Admin): Admin = adminRepository.save(admin)

    fun deleteById(id: Long) = adminRepository.deleteById(id)

    fun edit (id: Long, admin: Admin): Admin {
        val adminToEdit = findById(id)
        adminToEdit.name = admin.name
        adminToEdit.password = admin.password
        return save(adminToEdit)
    }
}