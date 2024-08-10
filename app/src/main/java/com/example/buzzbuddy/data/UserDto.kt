package com.example.buzzbuddy.data

data class UserDto(
    var user_first_name: String,
    var user_last_name: String,
    var user_phone: String
) {
    var id: Int = -1
    constructor(id: Int, user_first_name: String, user_last_name: String, user_phone: String): this(user_first_name, user_last_name, user_phone)
    {
        this.id = id
    }
}