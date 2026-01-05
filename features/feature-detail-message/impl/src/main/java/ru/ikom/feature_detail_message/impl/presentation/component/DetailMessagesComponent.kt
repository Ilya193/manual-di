package ru.ikom.feature_detail_message.impl.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.api.DetailMessageFeature
import ru.ikom.feature_detail_message.impl.di.DetailMessageContainer
import ru.ikom.feature_detail_message.impl.di.DetailMessageDependencies

class DetailMessagesComponent(
    private val feature: DetailMessageFeature,
    private val deps: DetailMessageDependencies,
    private val position: Int,
    private val messagesRepository: MessagesRepository = deps.messagesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        val message = messagesRepository.getMessageByPosition(position)
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
        fun create(
            feature: () -> DetailMessageFeature,
            deps: () -> DetailMessageDependencies,
            position: Int
        ) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailMessagesComponent(feature(), deps(), position) as T
                }
            }
    }
}