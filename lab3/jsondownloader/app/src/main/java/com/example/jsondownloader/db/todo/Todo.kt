package com.example.jsondownloader.db.todo

class Todo {
    var id: Int = 0
    var title: String = ""
    var completed: Boolean = false
    var userId: Int = 0

    constructor(){}

    constructor(id: Int, title: String, completed: Boolean, userId: Int){
        this.id = id
        this.title = title
        this.completed = completed
        this.userId = userId
    }
}