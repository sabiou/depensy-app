package dev.farouk.depensy.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.farouk.depensy.core.livedata.Event

open class ReselectViewModel : ViewModel() {
    private val _navigateReselect = MutableLiveData<Event<Any>>()
    val navigateReselect: LiveData<Event<Any>>
        get() = _navigateReselect

    fun reselect() {
        _navigateReselect.value = Event(Any())
    }
}