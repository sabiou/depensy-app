package xyz.godi.budgetmanager.core.viewmodel

import xyz.godi.budgetmanager.core.livedata.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ReselectViewModel : ViewModel() {
    private val _navigateReselect = MutableLiveData<Event<Any>>()
    val navigateReselect: LiveData<Event<Any>>
        get() = _navigateReselect

    fun reselect() {
        _navigateReselect.value = Event(Any())
    }
}