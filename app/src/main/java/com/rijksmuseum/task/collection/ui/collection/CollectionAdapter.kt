package com.rijksmuseum.task.collection.ui.collection

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rijksmuseum.task.R
import com.rijksmuseum.task.collection.ui.collection.model.CollectionAdapterItem
import com.rijksmuseum.task.databinding.CollectionListItemBinding
import com.rijksmuseum.task.databinding.ListItemHeaderBinding


class CollectionAdapter(
    onItemClick: (CollectionAdapterItem.Body) -> Unit
) : PagingDataAdapter<CollectionAdapterItem, CollectionAdapter.BaseViewHolder>(COMPARATOR) {

    private val onClickListener = View.OnClickListener {
        onItemClick.invoke(it.tag as CollectionAdapterItem.Body)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.run {
            itemToViewType(this)
        } ?: INVALID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            BODY -> {
                val binding = CollectionListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return BodyViewHolder(binding, onClickListener)
            }
            else -> {
                val binding = ListItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return if (viewType == HEADER)
                    TitleViewHolder(binding)
                else
                    InvalidViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    class BodyViewHolder(
        private val binding: CollectionListItemBinding,
        private val clickListener: View.OnClickListener
    ) : BaseViewHolder(binding.root) {

        override fun bind(collectionAdapterItem: CollectionAdapterItem) {
            val body = collectionAdapterItem.asBody()

            binding.title.text = body.title
            binding.description.text = body.description
            binding.identifier.text = itemView.context.getString(R.string.id_param, body.id)
            binding.author.text = itemView.context.getString(R.string.author_param, body.maker)

            with(binding.webLink) {
                if (body.link == null) {
                    isVisible = false
                } else {
                    isVisible = true
                    val htmlUrl = itemView.context.getString(R.string.visit_page, body.link)
                    text = Html.fromHtml(htmlUrl, FROM_HTML_MODE_LEGACY)
                }
            }

            Glide.with(itemView.context)
                .load(body.imageUrl)
                .into(binding.image)

            with(itemView) {
                tag = body
                setOnClickListener(clickListener)
            }
        }

        override fun onViewRecycled() {
            with(binding.image) {
                Glide.with(this).clear(this)
                setImageDrawable(null)
            }
        }
    }

    class TitleViewHolder
        (
        private val binding: ListItemHeaderBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(collectionAdapterItem: CollectionAdapterItem) {
            val header = collectionAdapterItem.asHeader()
            binding.title.text = header.title
        }
    }

    class InvalidViewHolder(
        private val binding: ListItemHeaderBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(collectionAdapterItem: CollectionAdapterItem) {
            binding.title.text = itemView.context.getString(R.string.loading)
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun onViewRecycled() {
            // override this method for clean up resources
        }

        abstract fun bind(collectionAdapterItem: CollectionAdapterItem)
    }

    companion object {

        private const val HEADER = 1 shl 0
        private const val BODY = 1 shl 1
        private const val INVALID = 1 shl 2

        private fun itemToViewType(adapterModel: CollectionAdapterItem): Int {
            return when (adapterModel) {
                is CollectionAdapterItem.Body -> BODY
                is CollectionAdapterItem.Header -> HEADER
            }
        }

        val COMPARATOR = object : DiffUtil.ItemCallback<CollectionAdapterItem>() {
            override fun areContentsTheSame(
                oldItem: CollectionAdapterItem,
                newItem: CollectionAdapterItem
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: CollectionAdapterItem,
                newItem: CollectionAdapterItem
            ): Boolean =
                (oldItem.isBody() && newItem.isBody() && oldItem.asBody().id == newItem.asBody().id)
                        || (oldItem.isHeader() && newItem.isHeader() && oldItem.asHeader() == newItem.asHeader())
        }
    }


}