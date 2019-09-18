package com.app.android.ibet.fragment.AllGames

object SampleFilter {
    fun getFilter(): MutableList<FilterListItem>{
        val filter = mutableListOf<FilterListItem>()
        filter.add(
            FilterListItem(
                id = 1,
                name = "Provider",
                data = mutableListOf(
                    FilterListItem(
                        id = 5,
                        name = "Netent"
                    ),
                    FilterListItem(
                        id = 6,
                        name = "Play&apos;n Go"
                    ),
                    FilterListItem(
                        id = 7,
                        name = "Big Time Gaming"
                    ),
                    FilterListItem(
                        id = 8,
                        name = "Microgaming"
                    ),
                    FilterListItem(
                        id = 9,
                        name = "Quickspin"
                    )

                )
            )

        )
        filter.add(
            FilterListItem(
                id = 2,
                name = "Features",
                data = mutableListOf(
                    FilterListItem(
                        id = 21,
                        name = "Stacked Wilds"
                    ),
                    FilterListItem(
                        id = 22,
                        name = "Expanding Wilds"
                    ),
                    FilterListItem(
                        id = 23,
                        name = "Special Wilds"
                    )


                )
            )
        )
        filter.add(
            FilterListItem(
                id = 3,
                name = "Theme",
                data = mutableListOf(
                    FilterListItem(
                        id = 31,
                        name = "Egypt"
                    ),
                    FilterListItem(
                        id = 32,
                        name = "Oriental"
                    ),
                    FilterListItem(
                        id = 33,
                        name = "Mythology"
                    )

                )
            )
        )
        filter.add(
            FilterListItem(
                id = 4,
                name = "Jackpot",
                data = mutableListOf(
                    FilterListItem(
                        id = 41,
                        name = "Daily"
                    ),
                    FilterListItem(
                        id = 42,
                        name = "Mystery"
                    ),
                    FilterListItem(
                        id = 43,
                        name = "Mega"
                    )
                )
            )
        )
        return filter
    }
}