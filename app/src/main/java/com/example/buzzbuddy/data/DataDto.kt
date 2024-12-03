package com.example.buzzbuddy.data

data class DataDto(
    var data_phone: String,
    var data_last_log: Long,
    var data_header_color: Int
) {
    var id: Int = -1
    constructor(id: Int, data_phone: String, data_last_log: Long, data_header_color: Int): this(data_phone, data_last_log, data_header_color)
    {
        this.id = id
    }
}