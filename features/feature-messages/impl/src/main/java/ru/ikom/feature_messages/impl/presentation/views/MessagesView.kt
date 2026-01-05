package ru.ikom.feature_messages.impl.presentation.views

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ikom.feature_messages.impl.R
import ru.ikom.feature_messages.impl.presentation.adapter.MessagesAdapter
import ru.ikom.feature_messages.impl.presentation.component.MessagesComponent
import ru.ikom.feature_messages.impl.presentation.model.MessageUi
import ru.ikom.ui.diff.BaseView
import ru.ikom.ui.diff.diff

private interface MessagesView : BaseView<MessagesView.Model> {

    data class Model(
        val messages: List<MessageUi>,
    )
}

private val stateToModel: MessagesComponent.State.() -> MessagesView.Model =
    {
        MessagesView.Model(
            messages = messages,
        )
    }

class MessagesViewImpl(
    private val root: View,
    private val component: MessagesComponent,
    lifecycleScope: CoroutineScope,
) {

    private val binding = Binding(root)

    private val adapter = MessagesAdapter(component::onClickMessage)

    private val viewRenderer =
        diff {
            diff(get = MessagesView.Model::messages, compare = { a, b -> a === b }, set = adapter::submitList)
        }

    init {
        setupMessages()

        lifecycleScope.launch {
            component.state.map(stateToModel).collect {
                viewRenderer.render(it)
            }
        }
    }

    private fun setupMessages() {
        binding.messages.adapter = adapter
        binding.messages.setHasFixedSize(true)
        binding.messages.addItemDecoration(DividerItemDecoration(root.context, RecyclerView.VERTICAL))
    }
}

private class Binding(val root: View) {

    val messages = root.findViewById<RecyclerView>(R.id.messages)
}