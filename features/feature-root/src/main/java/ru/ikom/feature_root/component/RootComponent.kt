package ru.ikom.feature_root.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_detail_message.di.DetailMessageDeps
import ru.ikom.feature_detail_message.presentation.component.DetailMessagesFeature
import ru.ikom.feature_messages.di.MessagesDeps
import ru.ikom.feature_messages.presentation.component.MessagesFeature
import ru.ikom.feature_root.detailMessageContent
import ru.ikom.feature_root.messagesContent

interface RootFeature {
    val messagesDeps: MessagesDeps
    val detailMessageDeps: DetailMessageDeps
}

class RootComponent(
    private val feature: RootFeature,
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(messagesContent())
    }

    fun messagesFeature(): MessagesFeature =
        object : MessagesFeature {
            override val deps: MessagesDeps = feature.messagesDeps

            override fun onOpenDetails(index: Int) =
                router.navigateTo(detailMessageContent(index))

        }

    fun detailMessageFeature(): DetailMessagesFeature =
        object : DetailMessagesFeature {
            override val deps: DetailMessageDeps = feature.detailMessageDeps

            override fun onBack() = router.exit()
        }
}

internal fun createRootComponent(
    feature: () -> RootFeature,
) = object : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RootComponent(feature()) as T
    }
}