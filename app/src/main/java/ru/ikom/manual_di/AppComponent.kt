package ru.ikom.manual_di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_root.RootFeatureLaunch
import ru.ikom.feature_root.component.RootFeature
import ru.ikom.manual_di.di.AppContainer

class AppComponent(
    private val appContainer: AppContainer,
    private val rootFeatureLaunch: RootFeatureLaunch,
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(rootFeatureLaunch.launch())
    }

    fun rootFeature(): RootFeature =
        object : RootFeature {
            override fun onCloseApp() { }
        }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun factory(
            appContainer: AppContainer,
            rootFeatureLaunch: RootFeatureLaunch,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppComponent(appContainer, rootFeatureLaunch) as T
            }
        }
    }
}