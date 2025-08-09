package ru.ikom.manual_di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_detail_message.di.DetailMessagesFeatureComponent
import ru.ikom.feature_detail_message.presentation.component.DetailMessagesFeature
import ru.ikom.feature_messages.presentation.component.MessagesFeature
import ru.ikom.feature_messages.di.MessagesFeatureComponent
import ru.ikom.manual_di.di.AppContainer
import ru.ikom.manual_di.screens.detailMessageContent
import ru.ikom.manual_di.screens.messagesContent

class RootComponent(
    private val appContainer: AppContainer
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation() {
        router.newRootScreen(messagesContent())
    }

    fun messagesFeature(): MessagesFeature =
        object : MessagesFeature {
            override val component: MessagesFeatureComponent =
                MessagesFeatureComponent(appContainer)

            override fun onOpenDetails(index: Int) {
                router.navigateTo(detailMessageContent(index))
            }
        }

    fun detailMessageFeature(): DetailMessagesFeature =
        object : DetailMessagesFeature {

            init {
                println("s149 init details feature")
            }

            override val component: DetailMessagesFeatureComponent =
                DetailMessagesFeatureComponent(appContainer)

            override fun onBack() {
                router.exit()
            }

        }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun factory(appContainer: AppContainer) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RootComponent(appContainer) as T
            }
        }
    }
}