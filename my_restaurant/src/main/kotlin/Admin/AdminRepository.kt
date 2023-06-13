import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : CrudRepository<Admin, Long> {

}