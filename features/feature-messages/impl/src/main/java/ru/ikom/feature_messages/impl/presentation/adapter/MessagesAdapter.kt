package ru.ikom.feature_messages.impl.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ikom.feature_messages.impl.R
import ru.ikom.feature_messages.impl.presentation.model.MessageUi

class MessagesAdapter(
    private val onClick: (Int) -> Unit,
) : ListAdapter<MessageUi, MessagesAdapter.ViewHolder>(DiffMessages()) {

    inner class ViewHolder(
        private val root: View
    ) : RecyclerView.ViewHolder(root) {

        private val textView = root as TextView

        init {
            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) onClick(position)
            }
        }

        fun bind(item: MessageUi) {
            textView.text = item.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DiffMessages : DiffUtil.ItemCallback<MessageUi>() {
    override fun areItemsTheSame(
        oldItem: MessageUi,
        newItem: MessageUi
    ): Boolean =
        oldItem.message == newItem.message

    override fun areContentsTheSame(
        oldItem: MessageUi,
        newItem: MessageUi
    ): Boolean =
        oldItem.message == newItem.message


}