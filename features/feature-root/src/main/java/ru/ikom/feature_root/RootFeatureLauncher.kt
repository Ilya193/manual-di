package ru.ikom.feature_root

interface RootFeatureLauncher {

    fun screen(): RootFeatureScreen
}

fun RootFeatureLauncher(
    rootFeatureScreen: RootFeatureScreen,
) = object : RootFeatureLauncher {

    override fun screen(): RootFeatureScreen = rootFeatureScreen
}