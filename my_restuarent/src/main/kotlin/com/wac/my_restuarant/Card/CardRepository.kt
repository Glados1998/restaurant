package com.wac.my_restuarant.Card

import org.springframework.stereotype.Repository

@Repository
interface CardRepository : org.springframework.data.repository.CrudRepository<Card, Long> {}