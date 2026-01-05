package ru.ikom.feature_messages.impl.presentation.component

import ru.ikom.domain_messages.MessagesRepository

interface MessagesFeatureDependencies {
    val messagesRepository: MessagesRepository
}

fun MessagesFeatureDependencies(
    messagesRepository: MessagesRepository
) =
    object : MessagesFeatureDependencies {
        override val messagesRepository: MessagesRepository = messagesRepository
    }