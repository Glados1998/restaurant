package Menu

import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : org.springframework.data.repository.CrudRepository<Menu, Long> {}