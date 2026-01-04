package ru.ikom.feature_detail_message.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessageContainer
import ru.ikom.feature_detail_message.di.DetailMessageDeps

interface DetailMessageFeature {

    fun interface Factory {
        operator fun invoke(
            onBack: () -> Unit
        )
    }
}

fun defaultDetailMessageFeatureFactory(
    detailMessageDeps: DetailMessageDeps
) =
    DetailMessageFeature.Factory {
        DefaultDetailMessageFeature(
            detailMessageDeps = detailMessageDeps
        )
    }

internal class DefaultDetailMessageFeature(
    val detailMessageDeps: DetailMessageDeps
) : DetailMessageDeps

internal class DetailMessagesComponent(
    private val feature: DefaultDetailMessageFeature,
    private val position: Int,
    private val container: DetailMessageContainer = DetailMessageContainer(feature.deps),
    private val messagesRepository: MessagesRepository = container.messagesRepository,
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
        fun create(feature: () -> DetailMessageFeature, position: Int) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailMessagesComponent(feature(), position) as T
                }
            }
    }
}