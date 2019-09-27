package com.app.android.ibet.fragment.AllGames

data class FilterListItem (val name: String = "",
                           val id: Int,
                           var data: MutableList<FilterListItem> = mutableListOf()
)