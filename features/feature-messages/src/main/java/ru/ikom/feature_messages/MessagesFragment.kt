package ru.ikom.feature_messages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.ikom.feature_messages.presentation.component.MessagesComponent
import ru.ikom.feature_messages.presentation.component.MessagesFeature
import ru.ikom.feature_messages.presentation.views.MessagesViewImpl

class MessagesFragment(
    private val feature: () -> MessagesFeature,
) : Fragment(R.layout.fragment_messages) {

    private val component: MessagesComponent by viewModels {
        MessagesComponent.create(feature())
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