package dev.farouk.depensy.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.farouk.depensy.ui.history.HistoryViewModel

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(application) as T
        }
        // else, throw an exception
        throw IllegalArgumentException("Unknow viewModel class")
    }
}