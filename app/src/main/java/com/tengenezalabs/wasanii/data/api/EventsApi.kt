package com.tengenezalabs.wasanii.data.api

import com.tengenezalabs.wasanii.data.responses.EventsResponse
import retrofit2.http.HTTP
import retrofit2.http.Query

interface EventsApi {

    @HTTP(method = "GET", path = "convert", hasBody = false)
    suspend fun getEvents(
        @Query("url")fetchFrom: String
    ) : EventsResponse
}