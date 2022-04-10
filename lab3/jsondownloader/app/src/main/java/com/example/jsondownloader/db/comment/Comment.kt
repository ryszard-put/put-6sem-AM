package com.example.jsondownloader.db.comment

class Comment {
    var id: Int = 0
    var name: String = ""
    var body: String = ""
    var email: String = ""
    var postId: Int = 0

    constructor(){}

    constructor(id: Int, name: String, body: String, email: String, postId: Int){
        this.id = id
        this.name = name
        this.body = body
        this.email = email
        this.postId = postId
    }
}