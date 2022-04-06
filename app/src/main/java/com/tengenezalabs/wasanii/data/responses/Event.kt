package com.tengenezalabs.wasanii.data.responses

data class Event(
    val author: Author,
    val content_html: String,
    val date_published: String,
    val guid: String,
    val summary: String,
    val title: String,
    val url: String
)