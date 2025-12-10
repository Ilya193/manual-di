package ru.ikom.feature_messages.di

import ru.ikom.domain_messages.MessagesRepository

interface MessagesContainer {

    val messagesRepository: MessagesRepository
}

interface MessagesDeps {

    val messagesRepository: MessagesRepository
}

internal fun MessagesFeatureContainer(deps: MessagesDeps): MessagesContainer =
    object : MessagesContainer {
        override val messagesRepository: MessagesRepository = deps.messagesRepository
    }