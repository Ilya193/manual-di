package ru.ikom.feature_messages.impl.di

import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_messages.impl.presentation.component.MessagesFeatureDependencies

interface MessagesContainer {

    val messagesRepository: MessagesRepository
}