package ru.ikom.data.messages.di

import ru.ikom.data.messages.impl.DefaultMessagesRepository
import ru.ikom.domain_messages.MessagesModule
import ru.ikom.domain_messages.MessagesRepository

interface DefaultMessagesModule : MessagesModule

fun DefaultMessagesModule(): DefaultMessagesModule =
    object : DefaultMessagesModule {
        override val messagesRepository: MessagesRepository = DefaultMessagesRepository()
    }