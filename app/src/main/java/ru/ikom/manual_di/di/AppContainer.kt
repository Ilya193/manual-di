package ru.ikom.manual_di.di

import android.app.Application
import ru.ikom.domain_messages.MessagesRepository
import ru.ikom.feature_detail_message.di.DetailMessageDeps
import ru.ikom.feature_messages.di.MessagesDeps

interface AppContainer {

    fun provideMessagesDeps(): MessagesDeps

    fun provideDetailMessageDeps(): DetailMessageDeps
}

class DefaultAppContainer(
    private val application: Application,
) : AppContainer {

    private val coreModule: CoreModule by lazy {
        DefaultCoreModule()
    }

    private val featureProvider: FeatureProvider by lazy {
        DefaultFeatureProvider(coreModule)
    }

    override fun provideMessagesDeps(): MessagesDeps = featureProvider.provideMessagesDeps()

    override fun provideDetailMessageDeps(): DetailMessageDeps = featureProvider.provideDetailMessageDeps()
}