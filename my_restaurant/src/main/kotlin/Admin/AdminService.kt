import org.springframework.stereotype.Service

@Service
class AdminService(private val adminRepository: AdminRepository) {
    fun findAll(): MutableIterable<Admin> = adminRepository.findAll()

    fun save(admin: Admin) = adminRepository.save(admin)

    fun findById(id: Long): Admin = adminRepository.findById(id).orElseThrow { AdminNotFoundException(id) }

    fun deleteById(id: Long) = adminRepository.deleteById(id)

    fun update(id: Long, admin: Admin) {
        val adminToUpdate = adminRepository.findById(id).orElseThrow { AdminNotFoundException(id) }
        adminToUpdate.name = admin.name
        adminToUpdate.passwordHash = admin.passwordHash
        adminToUpdate.email = admin.email
        adminRepository.save(adminToUpdate)
    }

    fun AdminNotFoundException(id: Long): Exception {
        return Exception("there has been an error")
    }

}