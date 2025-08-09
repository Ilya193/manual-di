package ru.ikom.manual_di.di

import android.app.Application
import ru.ikom.data.messages.di.MessagesDataModule
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessagesFeatureComponentDeps
import ru.ikom.feature_messages.di.MessagesFeatureComponentDeps

interface AppContainer :
    MessagesFeatureComponentDeps,
    DetailMessagesFeatureComponentDeps {

    val messagesDataModule: MessagesDataModule
}

class DefaultAppContainer(
    private val application: Application
) : AppContainer {

    override val messagesDataModule: MessagesDataModule by lazy {
        MessagesDataModule()
    }

    override val messagesRepository: MessagesRepository
        get() = messagesDataModule.messagesRepository

}