package com.example.jsondownloader.db.user

class User {
    var id : Int = 0
    var name : String = ""
    var email : String = ""

    constructor(){}

    constructor(id: Int, name: String, email: String) {
        this.id = id
        this.name = name
        this.email = email
    }
}