package com.tengenezalabs.wasanii.data.api

import com.tengenezalabs.wasanii.data.models.responses.EventsResponse
import retrofit2.http.HTTP
import retrofit2.http.Query

interface EventsApi {

    @HTTP(method = "GET", path = "api.json", hasBody = false)
    suspend fun getEvents(
        @Query("rss_url")fetchFrom: String,
        @Query("api_key")apiKey: String,
        @Query("count")count: Int
    ) : EventsResponse
}