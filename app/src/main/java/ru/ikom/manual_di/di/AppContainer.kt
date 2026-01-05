package ru.ikom.manual_di.di

import android.app.Application
import ru.ikom.feature_detail_message.impl.di.DetailMessageDependencies
import ru.ikom.feature_detail_message.impl.presentation.defaultDetailMessageScreen
import ru.ikom.feature_messages.impl.presentation.component.MessagesFeatureDependencies
import ru.ikom.feature_messages.impl.presentation.defaultMessagesScreen
import ru.ikom.feature_root.RootFeatureScreen
import ru.ikom.feature_root.component.RootFeatureDependencies
import ru.ikom.feature_root.defaultRootScreen

interface AppContainer {

    fun provideRootFeatureScreen(): RootFeatureScreen
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

    private fun internalRootFeatureDependencies() =
        RootFeatureDependencies(
            messagesFeatureScreen = defaultMessagesScreen(::internalMessagesFeatureDependencies),
            detailMessageFeatureScreen = defaultDetailMessageScreen(::internalDetailMessageDependencies)
        )

    private fun internalMessagesFeatureDependencies() =
        MessagesFeatureDependencies(coreModule.messagesModule.messagesRepository)

    private fun internalDetailMessageDependencies() =
        DetailMessageDependencies(coreModule.messagesModule.messagesRepository)

    override fun provideRootFeatureScreen(): RootFeatureScreen =
        defaultRootScreen(::internalRootFeatureDependencies)
}