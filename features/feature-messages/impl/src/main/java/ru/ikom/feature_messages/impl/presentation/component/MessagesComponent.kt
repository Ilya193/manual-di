package ru.ikom.feature_messages.impl.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_messages.api.MessagesFeature
import ru.ikom.feature_messages.impl.presentation.model.MessageUi

class MessagesComponent(
    private val feature: MessagesFeature,
    private val dependencies: MessagesFeatureDependencies,
    private val messagesRepository: MessagesRepository = dependencies.messagesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())

    val state = _state.asStateFlow()

    init {
        val messages = messagesRepository.messages()

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

        fun create(
            feature: () -> MessagesFeature,
            dependencies: () -> MessagesFeatureDependencies,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MessagesComponent(feature(), dependencies()) as T
            }
        }
    }
}