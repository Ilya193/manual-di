package ru.ikom.feature_detail_message.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.ikom.feature_detail_message.R
import ru.ikom.feature_detail_message.presentation.component.DetailMessagesComponent
import ru.ikom.feature_detail_message.presentation.component.DetailMessagesFeature

class DetailMessageFragment(
    private val feature: () -> DetailMessagesFeature,
) : Fragment(R.layout.detail_message_fragment) {

    private var position = 0

    private val component: DetailMessagesComponent by viewModels {
        DetailMessagesComponent.create(feature, position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        position = requireArguments().getInt(POSITION_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageView = view.findViewById<TextView>(R.id.message)

        view.setOnClickListener {
            component.onClickBack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            component.state.collect {
                messageView.text = it.message
            }
        }
    }

    fun setArgument(position: Int): DetailMessageFragment {
        arguments = bundleOf(POSITION_KEY to position)

        return this
    }
}

private const val POSITION_KEY = "position_key"