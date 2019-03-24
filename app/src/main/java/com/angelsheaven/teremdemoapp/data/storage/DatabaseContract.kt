package com.angelsheaven.teremdemoapp.data.storage

const val DATABASE_NAME = "teremDemoApp"

const val TABLE_NEWS = "news"

const val TABLE_READ_NEWS = "read_news"

const val TABLE_SAVED_NEWS = "saved_news"

const val SELECT_NEWS_DETAIL = "SELECT * FROM $TABLE_NEWS WHERE id = %d"

const val DELETE_ALL_NEWS = "DELETE FROM $TABLE_NEWS"

const val DELETE_SPECIFIC_NEWS = "DELETE FROM $TABLE_NEWS WHERE id ="

