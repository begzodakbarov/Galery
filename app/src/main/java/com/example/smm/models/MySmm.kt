package com.example.smm.models

class MySmm {
    var name : String? = null
    var imageLink:String? = null

    constructor(name: String?, imageLink: String?) {
        this.name = name
        this.imageLink = imageLink
    }

    constructor()

    override fun toString(): String {
        return "MySmm(name=$name, imageLink=$imageLink)"
    }
}