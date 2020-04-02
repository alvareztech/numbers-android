package tech.alvarez.numbers.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_channel.view.*
import tech.alvarez.numbers.R
import tech.alvarez.numbers.model.Item
import tech.alvarez.numbers.util.inflate
import tech.alvarez.numbers.util.loadImage

interface OnItemClickListener {
    fun onItemClick(channel: Item?)
}

class SearchChannelsAdapter(onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SearchChannelsAdapter.ChannelHolder>() {
    private var channels: List<Item> = listOf()
    private val onItemClickListener: OnItemClickListener = onItemClickListener

    fun updateChannels(channels: List<Item>) {
        this.channels = channels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val inflatedView = parent.inflate(R.layout.item_channel, false)
        return ChannelHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        val item: Item = channels[position]
        holder.bind(item)
        holder.setOnItemClickListener(item, onItemClickListener)
    }

    override fun getItemCount() = channels.size

    class ChannelHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val photoImageView = view.photoImageView
        private val titleTextView = view.titleTextView
        private val descTextView = view.descTextView
        private val addButton = view.addButton

        fun setOnItemClickListener(
            channel: Item?,
            onItemClickListener: OnItemClickListener
        ) {
            addButton.setOnClickListener { onItemClickListener.onItemClick(channel) }
        }

        fun bind(item: Item) {
            titleTextView.text = item.snippet?.title
            descTextView.text = item.snippet?.description
            item.snippet?.thumbnails?.default?.url.let {
                photoImageView.loadImage(it)
            }
        }
    }
}
