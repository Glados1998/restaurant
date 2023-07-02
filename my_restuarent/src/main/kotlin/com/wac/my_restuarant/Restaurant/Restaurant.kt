package com.wac.my_restuarant.Restaurant

import jakarta.persistence.*

@Entity
data class Restaurant(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 1,
    var name: String = "",
    var url: String = "",
    var streetAddress: String = "",
    var streetNumber: String = "",
    var city: String = "",
    var postalCode: String = "",
    var email: String = "",
    var phone: String = "",
    var mainColor: String = "",
    var secondaryColor: String = "",
    var fontColor: String = "",
    var linkColor : String = "",
    var imagePath: String = "",
    var description: String = ""
) {

    @get:Transient
    val isConfigured: Boolean
        get() = name.isNotEmpty() && url.isNotEmpty() && streetAddress.isNotEmpty() && streetNumber.isNotEmpty() && city.isNotEmpty() && postalCode.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && imagePath.isNotEmpty()

    @get:Transient
    val fullAddress: String
        get() = "$streetNumber $streetAddress, $postalCode $city"

}
