package ru.ikom.manual_di.screens

import ru.ikom.feature_detail_message.DetailMessageFragment
import ru.ikom.feature_messages.MessagesFragment
import ru.ikom.manual_di.navigation.defaultAnimationScreen
import ru.ikom.manual_di.navigation.defaultFragmentScreen
import ru.ikom.ui.R

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