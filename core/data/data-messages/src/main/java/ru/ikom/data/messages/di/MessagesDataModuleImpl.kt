package ru.ikom.data.messages.di

import ru.ikom.data.messages.impl.DefaultMessagesRepository
import ru.ikom.domain_messages.MessagesModuleApi
import ru.ikom.domain_messages.MessagesRepository

fun messagesModuleImpl(/*deps: MessagesDataModuleDeps*/): MessagesModuleApi {
    return object : MessagesModuleApi {
        override val messagesRepository: MessagesRepository
            get() = DefaultMessagesRepository()

    }
}