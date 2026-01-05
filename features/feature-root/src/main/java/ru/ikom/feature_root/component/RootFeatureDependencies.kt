package ru.ikom.feature_root.component

import ru.ikom.feature_detail_message.api.DetailMessageFeatureScreen
import ru.ikom.feature_messages.api.MessagesFeatureScreen

interface RootFeatureDependencies {
    val messagesFeatureScreen: MessagesFeatureScreen
    val detailMessageFeatureScreen: DetailMessageFeatureScreen
}

fun RootFeatureDependencies(
    messagesFeatureScreen: MessagesFeatureScreen,
    detailMessageFeatureScreen: DetailMessageFeatureScreen
) =
    object : RootFeatureDependencies {
        override val messagesFeatureScreen: MessagesFeatureScreen = messagesFeatureScreen
        override val detailMessageFeatureScreen: DetailMessageFeatureScreen = detailMessageFeatureScreen
    }