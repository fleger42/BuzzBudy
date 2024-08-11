package com.example.buzzbuddy.data
private const val DATA_ID = "data_id"
private const val DATA_PHONE = "data_phone"
private const val DATA_LAST_LOG = "data_last_log"
private const val DATA_HEADER_COLOR = "data_header_color"
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