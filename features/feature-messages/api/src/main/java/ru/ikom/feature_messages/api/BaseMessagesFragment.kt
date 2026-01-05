package ru.ikom.feature_messages.api

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseMessagesFragment(layout: Int) : Fragment(layout)

interface MessagesFeatureScreen {
    fun launch(): FragmentScreen

    fun content(feature: () -> MessagesFeature): BaseMessagesFragment
}