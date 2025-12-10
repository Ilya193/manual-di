package ru.ikom.feature_detail_message.di

import ru.ikom.domain_messages.MessagesRepository

interface DetailMessageContainer {

    val messagesRepository: MessagesRepository
}

interface DetailMessageDeps {

    val messagesRepository: MessagesRepository
}

internal fun DetailMessageContainer(deps: DetailMessageDeps): DetailMessageContainer =
    object : DetailMessageContainer {
        override val messagesRepository: MessagesRepository = deps.messagesRepository
    }