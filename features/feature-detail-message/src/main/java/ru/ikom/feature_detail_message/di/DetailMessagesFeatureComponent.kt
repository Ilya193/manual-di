package ru.ikom.feature_detail_message.di

import ru.ikom.domain_messages.MessagesModuleApi
import ru.ikom.domain_messages.MessagesRepository

interface DetailMessagesFeatureComponent {

    val repository: MessagesRepository
}

interface DetailMessagesFeatureComponentDeps {

    val messagesModuleApi: MessagesModuleApi
}

fun DetailMessagesFeatureComponent(deps: DetailMessagesFeatureComponentDeps): DetailMessagesFeatureComponent {
    return object : DetailMessagesFeatureComponent {
        override val repository: MessagesRepository by lazy { deps.messagesModuleApi.messagesRepository }

    }
}