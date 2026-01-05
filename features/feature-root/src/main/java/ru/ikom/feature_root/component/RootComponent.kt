package ru.ikom.feature_root.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_detail_message.api.DetailMessageFeature
import ru.ikom.feature_detail_message.api.DetailMessageFeatureScreen
import ru.ikom.feature_messages.api.MessagesFeature
import ru.ikom.feature_messages.api.MessagesFeatureScreen

class RootComponent(
    private val feature: RootFeature,
    val rootFeatureDependencies: RootFeatureDependencies,
    private val messagesFeatureScreen: MessagesFeatureScreen = rootFeatureDependencies.messagesFeatureScreen,
    private val detailMessageFeatureScreen: DetailMessageFeatureScreen = rootFeatureDependencies.detailMessageFeatureScreen,
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(messagesFeatureScreen.launch())
    }

    fun onBack() {
        router.exit()
    }

    fun messagesFeature(): MessagesFeature =
        object : MessagesFeature {
            override fun onOpenDetails(index: Int) =
                router.navigateTo(detailMessageFeatureScreen.sliderLaunch(index))
        }

    fun detailMessageFeature(): DetailMessageFeature =
        object : DetailMessageFeature {
            override fun onBack() = router.exit()
        }
}

internal fun createRootComponent(
    feature: () -> RootFeature,
    deps: () -> RootFeatureDependencies,
) = object : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RootComponent(feature(), deps()) as T
    }
}