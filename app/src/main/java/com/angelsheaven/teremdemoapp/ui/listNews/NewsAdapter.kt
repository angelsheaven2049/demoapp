/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.angelsheaven.teremdemoapp.ui.listNews

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.angelsheaven.teremdemoapp.data.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import kotlinx.android.synthetic.main.news_item_layout.view.*


class NewsAdapter(
    val onUserClickOnItem: (Int) -> Unit,
    private val onMarkRead: ((News?) -> Unit)?,
    val onUserBookmark: ((News?) -> Unit)?,
    val onUserUnBookmark: ((News?) -> Unit)?)
    : PagedListAdapter<News, NewsViewHolder>(diffCallback), MyLogger {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindTo(getItem(position))

        //Set listener to handle user click event on news item
        holder.itemView.setOnClickListener {
            if(position<itemCount) {
                this.getItem(position)?.run {
                    onUserClickOnItem(this.id)
                }
            }
        }

        holder.itemView.toggle_bookmark.setOnCheckedChangeListener { _, isChecked ->
            if(position<itemCount) {
                val currentItem = this.getItem(position)?.apply { saved = isChecked }
                if (isChecked) {
                    onUserBookmark?.invoke(currentItem)
                } else {
                    onUserUnBookmark?.invoke(currentItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(parent)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                    oldItem == newItem
        }
    }

    fun markRead(position: Int) {
        val currentItem = this.getItem(position)
        onMarkRead?.invoke(currentItem)
    }

}
