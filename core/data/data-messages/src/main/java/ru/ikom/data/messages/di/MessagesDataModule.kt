package ru.ikom.data.messages.di

import ru.ikom.data.messages.impl.DefaultMessagesRepository
import ru.ikom.domain_messages.MessagesRepository

interface MessagesDataModule {

    val messagesRepository: MessagesRepository
}

fun MessagesDataModule(/*deps: MessagesDataModuleDeps*/): MessagesDataModule {
    return object : MessagesDataModule {
        override val messagesRepository: MessagesRepository
            get() = DefaultMessagesRepository()

    }
}