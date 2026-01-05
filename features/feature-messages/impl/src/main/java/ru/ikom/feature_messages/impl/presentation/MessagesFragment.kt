package ru.ikom.feature_messages.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature_messages.api.BaseMessagesFragment
import ru.ikom.feature_messages.api.MessagesFeature
import ru.ikom.feature_messages.api.MessagesFeatureScreen
import ru.ikom.feature_messages.impl.R
import ru.ikom.feature_messages.impl.presentation.component.MessagesComponent
import ru.ikom.feature_messages.impl.presentation.component.MessagesFeatureDependencies
import ru.ikom.feature_messages.impl.presentation.views.MessagesViewImpl
import ru.ikom.ui.navigation.defaultFragmentScreen

fun defaultMessagesScreen(
    dependencies: () -> MessagesFeatureDependencies
) =
    object : MessagesFeatureScreen {
        override val tag: String get() = MessagesFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultFragmentScreen {
                it.get(MessagesFragment::class.java)
            }

        override fun content(feature: () -> MessagesFeature): BaseMessagesFragment =
            MessagesFragment(feature, dependencies)
    }

class MessagesFragment(
    private val feature: () -> MessagesFeature,
    private val dependencies: () -> MessagesFeatureDependencies,
) : BaseMessagesFragment(R.layout.fragment_messages) {

    private val component: MessagesComponent by viewModels {
        MessagesComponent.create(feature, dependencies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewImpl =
            MessagesViewImpl(
                root = view,
                component = component,
                lifecycleScope = viewLifecycleOwner.lifecycleScope
            )
    }
}