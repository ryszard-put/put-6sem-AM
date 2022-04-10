package com.example.jsondownloader.db.post

class Post {
    var id: Int = 0
    var title: String = ""
    var body: String = ""
    var userId: Int = 0

    constructor(){}

    constructor(id: Int, title: String, body: String, userId: Int){
        this.id = id
        this.title = title
        this.body = body
        this.userId = userId
    }
}