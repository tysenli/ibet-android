package com.app.android.ibet.activity.Navigation

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.app.android.ibet.R


data class NaviMenuItem(val id: Int,
                    @StringRes var titleRes: Int = R.string.menu,
                    var title: String = "",
                    @DrawableRes var iconRes: Int = R.drawable.ic_menu_gallery,
                    var badget: Int = 0,
                    var subMenus: MutableList<NaviMenuItem> = mutableListOf()
)