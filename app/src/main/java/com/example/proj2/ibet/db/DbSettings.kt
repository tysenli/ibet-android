package com.example.proj2.ibet.db

import android.provider.BaseColumns

class DbSettings {
    companion object {
        const val DB_NAME = "game.db"
        const val DB_VERSION = 1
    }

    class DBPlaylistEntry: BaseColumns {
        companion object {
            const val TABLE = "games"
            const val ID = BaseColumns._ID
            const val NAME = "name"

            const val IMAGE = "image"

        }
    }

}