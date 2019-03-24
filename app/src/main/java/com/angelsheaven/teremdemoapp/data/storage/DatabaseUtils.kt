package com.angelsheaven.teremdemoapp.data.storage

import com.angelsheaven.teremdemoapp.*

fun convertFilterIndexOptionToStringValue(selectedIndex: Int):String = when(selectedIndex){
    1 -> FILTER_BY_STORY

    2 -> FILTER_BY_JOB

    3 -> FILTER_BY_COMMENT

    4 -> FILTER_BY_POLL

    5 -> FILTER_BY_POLLOPT

    else -> FILTER_BY_ALL
}

fun convertSortIndexOptionToStringValue(selectedIndex: Int):String = when(selectedIndex){
    0 -> SORT_BY_AUTHOR

    1 -> SORT_BY_TIME

    2 -> SORT_BY_TITLE

    else -> SORT_BY_NONE
}

