package Menu

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository


@Repository
interface MenuRepository : JpaRepository<Menu, Long> {

}