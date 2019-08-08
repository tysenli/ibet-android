package com.app.android.ibet.model

import java.io.Serializable

class GameModel(): Serializable {
    private var name: String = ""
    private var image: String = ""

    constructor(

        name: String,
        image: String

    ): this() {

        this.name = name
        this.image = image


    }

    fun getName(): String {
        return this.name
    }

    fun getImage(): String {
        return this.image
    }


}