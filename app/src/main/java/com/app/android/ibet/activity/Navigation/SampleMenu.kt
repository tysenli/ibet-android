package com.app.android.ibet.activity.Navigation


import com.app.android.ibet.R
import com.app.android.ibet.activity.Navigation.NaviMenuItem
import java.util.*

object SampleMenu {

    fun getMenu(): MutableList<NaviMenuItem> {
        val menu = mutableListOf<NaviMenuItem>()

        menu.add(
                NaviMenuItem(
                        id = 1,
                        title = "SPORTS",
                        iconRes = R.drawable.trophy,
                        subMenus = mutableListOf(
                                NaviMenuItem(
                                        id = 11,
                                        title = "Football",
                                        iconRes = R.drawable.soccer
                                ),
                                NaviMenuItem(
                                        id = 12,
                                        title = "Basketball",
                                        iconRes = R.drawable.basketball
                                ),
                                NaviMenuItem(
                                        id = 13,
                                        title = "Tennis",
                                        iconRes = R.drawable.tennis
                                ),
                                NaviMenuItem(
                                        id = 14,
                                        title = "Ice Hockey",
                                        iconRes = R.drawable.ice
                                ),
                                NaviMenuItem(
                                        id = 15,
                                        title = "Golf",
                                        iconRes = R.drawable.combined_shape
                                ),
                                NaviMenuItem(
                                        id = 16,
                                        title = "Baseball",
                                        iconRes = R.drawable.baseball
                                ),
                                NaviMenuItem(
                                        id = 17,
                                        title = "American Football",
                                        iconRes = R.drawable.football
                                ),
                                NaviMenuItem(
                                        id = 18,
                                        title = "Badminton",
                                        iconRes = R.drawable.badminton
                                )
                        )
                )
        )

        menu.add(
                NaviMenuItem(
                        id = 2,
                        title = "LIVE CASINO",
                        iconRes = R.drawable.casino,
                        subMenus = mutableListOf(
                                NaviMenuItem(
                                        id = 21,
                                        title = "Galería",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 22,
                                        title = "Compartir",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 23,
                                        title = "Carousel",
                                        iconRes = R.drawable.ic_menu_gallery
                                )
                        )
                )
        )

        menu.add(
                NaviMenuItem(
                        id = 3,
                        title = "SLOTS",
                        iconRes = R.drawable.slots,
                        subMenus = mutableListOf(
                                NaviMenuItem(
                                        id = 31,
                                        title = "Galería",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 32,
                                        title = "Compartir",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 33,
                                        title = "Carousel",
                                        iconRes = R.drawable.ic_menu_gallery
                                )
                        )
                )
        )

        menu.add(
                NaviMenuItem(
                        id = 4,
                        title = "LOTTERY",
                        iconRes = R.drawable.lottery,
                        subMenus = mutableListOf(
                                NaviMenuItem(
                                        id = 31,
                                        title = "Galería",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 32,
                                        title = "Compartir",
                                        iconRes = R.drawable.ic_menu_gallery
                                ),
                                NaviMenuItem(
                                        id = 33,
                                        title = "Carousel",
                                        iconRes = R.drawable.ic_menu_gallery
                                )
                        )
                )
        )

        return menu
    }

    fun getItemWithoutSubMenus() = NaviMenuItem(
            id = Random().nextInt(1000),
            title = "New Menu",
            iconRes = R.drawable.ic_menu_gallery
    )

    fun getItemWithSubMenu() = NaviMenuItem(
            id = Random().nextInt(10),
            title = "New Menu",
            iconRes = R.drawable.ic_menu_gallery,
            subMenus = mutableListOf(
                    NaviMenuItem(
                            id = 31,
                            title = "Galería",
                            iconRes = R.drawable.ic_menu_gallery,
                            badget = 10
                    ),
                    NaviMenuItem(
                            id = 32,
                            title = "Compartir",
                            iconRes = R.drawable.ic_menu_gallery,
                            badget = 1
                    ),
                    NaviMenuItem(
                            id = 33,
                            title = "Carousel",
                            iconRes = R.drawable.ic_menu_gallery
                    )
            )
    )
}