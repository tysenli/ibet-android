package com.app.android.ibet.fragment

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.android.ibet.R
import com.app.android.ibet.activity.Navigation.NaviMenuItem

data class FilterListItem (val id: Int,
                           @StringRes var titleRes: Int = R.string.menu,
                           var title: String = "",
                           @DrawableRes var iconRes: Int = R.drawable.ic_menu_gallery,
                           var badget: Int = 0,
                           var subMenus: MutableList<NaviMenuItem> = mutableListOf())