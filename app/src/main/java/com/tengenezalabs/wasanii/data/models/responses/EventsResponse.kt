package com.tengenezalabs.wasanii.data.models.responses

data class EventsResponse(
    val feed: Feed,
    val items: List<Event>,
    val status: String
)