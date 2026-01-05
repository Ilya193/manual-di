package ru.ikom.feature_detail_message.impl.di

import ru.ikom.domain_messages.MessagesRepository

interface DetailMessageContainer {

    val messagesRepository: MessagesRepository
}

interface DetailMessageDependencies {
    val messagesRepository: MessagesRepository
}

fun DetailMessageDependencies(
    messagesRepository: MessagesRepository
) =
    object : DetailMessageDependencies {
        override val messagesRepository: MessagesRepository = messagesRepository
    }

internal fun DetailMessageContainer(deps: DetailMessageDependencies): DetailMessageContainer =
    object : DetailMessageContainer {
        override val messagesRepository: MessagesRepository = deps.messagesRepository
    }