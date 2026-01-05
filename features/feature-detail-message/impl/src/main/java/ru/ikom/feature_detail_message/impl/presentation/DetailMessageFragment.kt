package ru.ikom.feature_detail_message.impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.launch
import ru.ikom.feature_detail_message.api.BaseDetailMessageFragment
import ru.ikom.feature_detail_message.api.DetailMessageFeature
import ru.ikom.feature_detail_message.api.DetailMessageFeatureScreen
import ru.ikom.feature_detail_message.api.POSITION_KEY
import ru.ikom.feature_detail_message.impl.R
import ru.ikom.feature_detail_message.impl.di.DetailMessageDependencies
import ru.ikom.feature_detail_message.impl.presentation.component.DetailMessagesComponent
import ru.ikom.ui.navigation.defaultFragmentScreen

fun defaultDetailMessageScreen(
    dependencies: () -> DetailMessageDependencies
) =
    object : DetailMessageFeatureScreen {
        override val tag: String get() = DetailMessageFragment::class.java.simpleName

        override fun launch(position: Int): FragmentScreen =
            defaultFragmentScreen {
                it.get(DetailMessageFragment::class.java)
                    .setArgument(position)
            }

        override fun content(feature: () -> DetailMessageFeature): BaseDetailMessageFragment =
            DetailMessageFragment(feature, dependencies)
    }

class DetailMessageFragment(
    private val feature: () -> DetailMessageFeature,
    private val deps: () -> DetailMessageDependencies,
) : BaseDetailMessageFragment(R.layout.detail_message_fragment) {

    private var position = 0

    private val component: DetailMessagesComponent by viewModels {
        DetailMessagesComponent.create(feature, deps, position)
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
}