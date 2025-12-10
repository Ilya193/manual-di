package ru.ikom.manual_di.screens

import ru.ikom.feature_detail_message.presentation.DetailMessageFragment
import ru.ikom.feature_messages.presentation.MessagesFragment
import ru.ikom.feature_root.RootFragment
import ru.ikom.ui.navigation.defaultAnimationScreen
import ru.ikom.ui.navigation.defaultFragmentScreen
import ru.ikom.ui.R

fun rootContent() =
    defaultFragmentScreen {
        it.get(RootFragment::class.java)
    }