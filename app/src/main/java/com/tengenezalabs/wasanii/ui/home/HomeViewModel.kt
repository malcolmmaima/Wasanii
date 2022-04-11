package com.tengenezalabs.wasanii.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengenezalabs.wasanii.data.models.responses.EventsResponse
import com.tengenezalabs.wasanii.data.respository.APIResource
import com.tengenezalabs.wasanii.data.respository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EventsRepository
) : ViewModel() {

    private val _events: MutableLiveData<APIResource<EventsResponse>> = MutableLiveData()
    val events: LiveData<APIResource<EventsResponse>>
        get() = _events

    fun getEvents(fetchFrom: String) = viewModelScope.launch {
        _events.value = APIResource.Loading
        _events.value = repository.getEvents(fetchFrom)
    }

}