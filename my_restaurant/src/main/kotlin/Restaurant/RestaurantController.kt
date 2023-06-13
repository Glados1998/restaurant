package Restaurant

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class RestaurantController {

    @GetMapping
    fun index(): String {
        return "index"
    }
}