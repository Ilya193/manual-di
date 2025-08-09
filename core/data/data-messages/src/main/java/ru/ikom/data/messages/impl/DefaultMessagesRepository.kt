package ru.ikom.data.messages.impl

import ru.ikom.domain_messages.MessagesRepository

class DefaultMessagesRepository : MessagesRepository {

    private val _messages = List(100) {
        "item $it"
    }

    override fun messages(): List<String> = _messages

    override fun getMessageByPosition(position: Int): String {
        return _messages[position] // unsafe
    }
}