package tech.alvarez.numbers.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import tech.alvarez.numbers.R
import tech.alvarez.numbers.model.Item
import tech.alvarez.numbers.util.inflate

interface OnItemClickListener {
    fun onItemClick(channel: Item?)
}

class SearchChannelsAdapter(onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SearchChannelsAdapter.ChannelHolder>() {
    private var dataset: List<Item> = listOf()
    private val onItemClickListener: OnItemClickListener = onItemClickListener

    init {
//        mDb = AppDatabase.getDatabase(context.applicationContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val inflatedView = parent.inflate(R.layout.item_channel, false)
        return ChannelHolder(inflatedView)
    }

    override fun onBindViewHolder(
        holder: ChannelHolder,
        position: Int
    ) {
        val item: Item = dataset[position]
        holder.bindData(item)
        holder.setOnItemClickListener(item, onItemClickListener)
    }

    override fun getItemCount() = dataset.size

    class ChannelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoImageView: ImageView =
            itemView.findViewById<View>(R.id.photoImageView) as ImageView
        private val titleTextView: TextView =
            itemView.findViewById<View>(R.id.titleTextView) as TextView
        private val descTextView: TextView =
            itemView.findViewById<View>(R.id.descTextView) as TextView
        private val addButton: Button = itemView.findViewById<View>(R.id.addButton) as Button

        fun setOnItemClickListener(
            channel: Item?,
            onItemClickListener: OnItemClickListener
        ) {
            addButton.setOnClickListener { onItemClickListener.onItemClick(channel) }
        }

        fun bindData(item: Item) {
            titleTextView.text = item.snippet?.title
            descTextView.text = item.snippet?.description
            item.snippet?.thumbnails?.default?.url.let {

                Glide.with(itemView.context).load(it)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoImageView)
            }
        }
    }

    fun setDataset(dataset: List<Item>) {
        this.dataset = dataset
        notifyDataSetChanged()
    }
}
