package ru.ikom.manual_di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_detail_message.di.DetailMessageDeps
import ru.ikom.feature_messages.di.MessagesDeps
import ru.ikom.feature_root.component.RootFeature
import ru.ikom.manual_di.di.AppContainer
import ru.ikom.manual_di.screens.rootContent

class AppComponent(
    private val appContainer: AppContainer
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(rootContent())
    }

    fun rootFeature(): RootFeature =
        object : RootFeature {
            override val messagesDeps: MessagesDeps = appContainer.provideMessagesDeps()

            override val detailMessageDeps: DetailMessageDeps = appContainer.provideDetailMessageDeps()
        }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun factory(appContainer: AppContainer) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppComponent(appContainer) as T
            }
        }
    }
}