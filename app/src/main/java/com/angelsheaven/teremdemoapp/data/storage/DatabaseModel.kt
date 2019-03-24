package com.angelsheaven.teremdemoapp.data.storage

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = TABLE_NEWS, indices = [Index(value = ["type"])])
open class News(@PrimaryKey(autoGenerate = true) var roomId: Int = 0
                , var id: Int = 0
                , var deleted: Boolean = false
                , var type: String = ""
                , var by:String=""
                , var time: Long = 0
                , var text: String = ""
                , var dead: Boolean = false
                , var parent: String = ""
                , var poll: String = ""
                , var kids: List<Int>? = null
                , var url: String = ""
                , var score: Int = 0
                , var title: String = ""
                , var parts: String = ""
                , var descendants: Int = 0
                , var saved:Boolean = false){

    @Ignore
    private val formatter= SimpleDateFormat(DATE_TIME_FORMATTER_PATTERN, Locale.UK)

    fun getFormatTime():String {
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = Date(time)
        return formatter.format(dateTime)
    }

    override fun equals(other: Any?): Boolean {
        return ((other is News)
                && (this.id==other.id
                && this.deleted==other.deleted
                && this.type == other.type
                && this.by == other.by
                && this.time == other.time
                && this.dead == other.dead
                && this.parent == other.parent
                && this.poll == other.poll
                && this.kids == other.kids
                && this.url == other.url
                && this.score == other.score
                && this.title == other.title
                && this.parts == other.parts
                && this.descendants == other.descendants
                && this.saved == other.saved))
    }
}

@Entity(tableName = TABLE_READ_NEWS, indices = [Index(value = ["readTime"])])
data class ReadNews(var readTime: Date? = null): News(){
    fun createReadNews(news: News): ReadNews {
        this.id = news.id
        this.deleted = news.deleted
        this.type = news.type
        this.by = news.by
        this.time = news.time
        this.text = news.text
        this.dead = news.dead
        this.parent = news.parent
        this.poll = news.poll
        this.kids = news.kids
        this.url = news.url
        this.score = news.score
        this.title = news.title
        this.parts = news.parts
        this.saved = news.saved
        this.descendants = news.descendants
        return this
    }
}

@Entity(tableName = TABLE_SAVED_NEWS, indices = [Index(value = ["savedTime"])])
data class SavedNews(var savedTime: Date? = null): News(){
    fun createSavedNews(news: News): SavedNews {
        this.id = news.id
        this.deleted = news.deleted
        this.type = news.type
        this.by = news.by
        this.time = news.time
        this.text = news.text
        this.dead = news.dead
        this.parent = news.parent
        this.poll = news.poll
        this.kids = news.kids
        this.url = news.url
        this.score = news.score
        this.title = news.title
        this.parts = news.parts
        this.descendants = news.descendants
        this.saved = news.saved
        return this
    }
}

data class Account(val username:String="",val password:String="")
