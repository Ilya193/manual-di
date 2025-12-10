package ru.ikom.manual_di.di

import ru.ikom.data.messages.di.DefaultMessagesModule
import ru.ikom.domain_messages.MessagesModule

interface CoreModule {
    val messagesModule: MessagesModule
}

class DefaultCoreModule : CoreModule {

    override val messagesModule: MessagesModule by lazy {
        DefaultMessagesModule()
    }
}