package ru.ikom.domain_messages

interface MessagesRepository {

    fun messages(): List<String>

    fun getMessageByPosition(position: Int): String
}