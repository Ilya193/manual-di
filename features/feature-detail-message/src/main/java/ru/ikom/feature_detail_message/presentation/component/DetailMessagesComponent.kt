package ru.ikom.feature_detail_message.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessagesFeatureComponent

interface DetailMessagesFeature {

    val component: DetailMessagesFeatureComponent

    fun onBack()
}

class DetailMessagesComponent(
    private val feature: DetailMessagesFeature,
    private val position: Int,
) : ViewModel() {

    private val repository: MessagesRepository
        get() = feature.component.repository

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        val message = repository.getMessageByPosition(position)
        _state.update { it.copy(message = message) }
    }

    fun onClickBack() {
        feature.onBack()
    }

    data class State(
        val message: String = "",
    )

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun create(feature: DetailMessagesFeature, position: Int) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailMessagesComponent(feature, position) as T
            }
        }
    }
}