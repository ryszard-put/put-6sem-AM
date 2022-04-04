package com.example.numberguesser.DB.User

import android.provider.BaseColumns

class User {
    var _ID : Int = 0
    var username : String = ""
    var password : String = ""
    var currentScore : Int = 0

    constructor(){}

    constructor(id:Int, username:String, password:String, currentScore: Int){
        this._ID = id
        this.username = username
        this.password = password
        this.currentScore = currentScore
    }
}