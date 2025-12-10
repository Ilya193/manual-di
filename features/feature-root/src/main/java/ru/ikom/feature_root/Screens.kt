package ru.ikom.feature_root

import ru.ikom.feature_detail_message.presentation.DetailMessageFragment
import ru.ikom.feature_messages.presentation.MessagesFragment
import ru.ikom.ui.R
import ru.ikom.ui.navigation.defaultAnimationScreen
import ru.ikom.ui.navigation.defaultFragmentScreen

fun messagesContent() =
    defaultFragmentScreen {
        it.get(MessagesFragment::class.java)
    }

fun detailMessageContent(position: Int) =
    defaultAnimationScreen(
        enter = R.anim.slide_in_right,
        exit = R.anim.slide_out_left,
        popEnter = R.anim.slide_in_left,
        popExit = R.anim.slide_out_right,
    ) {
        it.get(DetailMessageFragment::class.java)
            .setArgument(position)
    }