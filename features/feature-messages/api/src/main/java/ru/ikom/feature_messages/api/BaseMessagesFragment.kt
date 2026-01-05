package ru.ikom.feature_messages.api

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseMessagesFragment(layout: Int) : Fragment(layout)

interface MessagesFeatureLaunch {
    fun launch(): FragmentScreen
}

interface MessagesFeatureContent {
    val tag: String
    fun content(feature: () -> MessagesFeature): BaseMessagesFragment
}

interface MessagesFeatureScreen : MessagesFeatureContent, MessagesFeatureLaunch