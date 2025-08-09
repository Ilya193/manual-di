package ru.ikom.feature_messages.di

import ru.ikom.domain_messages.MessagesRepository

interface MessagesFeatureComponent {

    val repository: MessagesRepository
}

interface MessagesFeatureComponentDeps {

    val messagesRepository: MessagesRepository
}

fun MessagesFeatureComponent(deps: MessagesFeatureComponentDeps): MessagesFeatureComponent {
    return object : MessagesFeatureComponent {
        override val repository: MessagesRepository by lazy { deps.messagesRepository }
    }
}