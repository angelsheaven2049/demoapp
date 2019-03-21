package com.angelsheaven.teremdemoapp.ui.listNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelsheaven.teremdemoapp.R
import com.angelsheaven.teremdemoapp.data.database.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import kotlinx.android.synthetic.main.news_item_layout.view.*

class NewsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.news_item_layout,parent,false)),MyLogger{

    fun bindTo(news:News?){
        news?.run {
            itemView.tv_title.text = this.title
            itemView.tv_author.text = this.by
            itemView.tv_create_time.text = this.getFormatTime()
            itemView.tv_url.text = this.url
            itemView.toggle_bookmark.isChecked = news.saved
        }
    }
}