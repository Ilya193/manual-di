package ru.ikom.manual_di.di

import android.app.Application
import ru.ikom.data.messages.di.messagesModuleImpl
import ru.ikom.domain_messages.MessagesModuleApi
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessagesFeatureComponentDeps
import ru.ikom.feature_messages.di.MessagesFeatureComponentDeps

interface AppContainer :
    MessagesFeatureComponentDeps,
    DetailMessagesFeatureComponentDeps

class DefaultAppContainer(
    private val application: Application
) : AppContainer {

    override val messagesModuleApi: MessagesModuleApi by lazy {
        messagesModuleImpl()
    }

    override val messagesRepository: MessagesRepository
        get() = messagesModuleApi.messagesRepository

}