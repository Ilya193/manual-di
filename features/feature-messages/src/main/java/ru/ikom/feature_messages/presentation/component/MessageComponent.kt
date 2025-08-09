package ru.ikom.feature_messages.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_messages.di.MessagesFeatureComponent
import ru.ikom.feature_messages.presentation.model.MessageUi

interface MessagesFeature {

    val component: MessagesFeatureComponent

    fun onOpenDetails(index: Int)
}

class MessagesComponent(
    private val feature: MessagesFeature
) : ViewModel() {

    private val repository: MessagesRepository
        get() = feature.component.repository

    private val _state = MutableStateFlow(State())

    val state = _state.asStateFlow()

    init {
        val messages = repository.messages()

        _state.update { it.copy(messages = messages.map(::MessageUi)) }
    }

    fun onClickMessage(position: Int) {
        feature.onOpenDetails(position)
    }

    data class State(
        val messages: List<MessageUi> = emptyList(),
    )

    @Suppress("UNCHECKED_CAST")
    companion object {

        fun create(feature: MessagesFeature) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MessagesComponent(feature) as T
            }
        }
    }
}