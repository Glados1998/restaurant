package com.wac.my_restuarant.Review

import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : org.springframework.data.repository.CrudRepository<Review, Long> {}