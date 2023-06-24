package com.wac.my_restuarant.Global

import com.wac.my_restuarant.Restaurant.Restaurant
import com.wac.my_restuarant.Restaurant.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalControllerAdvice {

    @Autowired
    private lateinit var restaurantService: RestaurantService

    @ModelAttribute("restaurant")
    fun restaurant(): Restaurant? {
        return restaurantService.getRestaurant() ?: Restaurant()
    }
}