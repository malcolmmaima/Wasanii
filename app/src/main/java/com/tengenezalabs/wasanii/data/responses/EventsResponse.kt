package com.tengenezalabs.wasanii.data.responses

data class EventsResponse (
    val description: String,
    val home_page_url: String,
    val items: List<Event>,
    val title: String,
    val version: String
)