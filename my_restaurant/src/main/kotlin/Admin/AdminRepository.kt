package Admin

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
public interface AdminRepository : JpaRepository<Admin, Long> {

}