package com.tengenezalabs.wasanii.data.respository

import com.tengenezalabs.wasanii.data.api.EventsApi
import com.tengenezalabs.wasanii.data.responses.EventsResponse
import javax.inject.Inject

interface EventsRepository {
    suspend fun getEvents(fetchFrom: String): APIResource<EventsResponse>
}

class EventsRepositoryImpl @Inject constructor(
    private val eventsApi: EventsApi
) : EventsRepository, BaseRepo() {


    override suspend fun getEvents(fetchFrom: String) = safeApiCall {
        eventsApi.getEvents(fetchFrom)
    }
}