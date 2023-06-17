package com.wac.my_restuarant.Menu

import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : org.springframework.data.repository.CrudRepository<Menu, Long> {}