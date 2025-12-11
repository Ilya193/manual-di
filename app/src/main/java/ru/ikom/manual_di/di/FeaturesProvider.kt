package ru.ikom.manual_di.di

import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessageDeps
import ru.ikom.feature_messages.di.MessagesDeps

interface FeatureProvider {
    fun provideMessagesDeps(): MessagesDeps

    fun provideDetailMessageDeps(): DetailMessageDeps
}

class DefaultFeatureProvider(
    private val coreModule: CoreModule
) : FeatureProvider {

    override fun provideMessagesDeps(): MessagesDeps =
        object : MessagesDeps {
            override val messagesRepository: MessagesRepository =
                coreModule.messagesModule.messagesRepository
        }

    override fun provideDetailMessageDeps(): DetailMessageDeps =
        object : DetailMessageDeps {
            override val messagesRepository: MessagesRepository =
                coreModule.messagesModule.messagesRepository
        }
}