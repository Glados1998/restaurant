package Admin

import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : org.springframework.data.repository.CrudRepository<Admin, Long> {}