package com.angelsheaven.teremdemoapp

import android.os.Bundle
import androidx.paging.PagedList


const val INITIALIZE_DATA = "is_initialized_data"

const val FILTER_BY_ALL = "all"

const val FILTER_BY_STORY = "story"

const val FILTER_BY_COMMENT = "comment"

const val FILTER_BY_JOB = "job"

const val FILTER_BY_POLL = "poll"

const val FILTER_BY_POLLOPT = "pollopt"

const val SORT_BY_AUTHOR = "`by`"

const val SORT_BY_TIME = "`time`"

const val SORT_BY_TITLE = "`title`"

const val SORT_BY_NONE = "none"

val configLoadData by lazy { PagedList.Config.Builder()
    .setPageSize(20)
    .setPrefetchDistance(20)
    .setEnablePlaceholders(false)
    .build() }

const val LAST_SEARCH_QUERY = "last_search_query"

const val DEFAULT_QUERY = ""

const val QUERY_VALUE = "query_value"

const val FILTER_VALUE = "filter_value"

const val SORT_VALUE = "sort_value"

const val DEFAULT_FILTER_OPTION_INDEX: Int = 0

const val DEFAULT_SORT_OPTION_INDEX: Int = -1

const val NOTIFICATION_NAME = "terem app notification channel"

const val PRIMARY_CHANNEL_ID = "101"

const val NOTIFICATION_DESCRIPTION = "Notification from terem app"

val defaultSearchCondition by lazy {
    Bundle().apply {
        this.putInt(
            FILTER_VALUE,
            DEFAULT_FILTER_OPTION_INDEX
        )
        this.putInt(SORT_VALUE, DEFAULT_SORT_OPTION_INDEX)
        this.putString(QUERY_VALUE, DEFAULT_QUERY)
    }
}

const val PREFS_FILENAME = "com.angelsheavens.teremdemoapp.prefs"









