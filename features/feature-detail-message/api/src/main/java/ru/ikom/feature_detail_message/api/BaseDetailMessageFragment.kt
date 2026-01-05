package ru.ikom.feature_detail_message.api

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

const val POSITION_KEY = "position_key"

abstract class BaseDetailMessageFragment(layout: Int) : Fragment(layout) {

    fun setArgument(position: Int): BaseDetailMessageFragment {
        arguments = bundleOf(POSITION_KEY to position)
        return this
    }
}

interface DetailMessageFeatureScreen {
    val tag: String

    fun launch(position: Int): FragmentScreen

    fun content(feature: () -> DetailMessageFeature): BaseDetailMessageFragment
}