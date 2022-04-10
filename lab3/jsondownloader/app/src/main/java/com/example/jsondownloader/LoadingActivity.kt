package com.example.jsondownloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import org.json.JSONArray
import org.json.JSONTokener
import java.net.URL

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Thread() {
            run {
//                db = DBHelper(this)

                val url = "https://jsonplaceholder.typicode.com"
                val body = URL("$url/users").readText()
                if (body != "") {
                    val data = JSONTokener(body).nextValue() as JSONArray
                    for(i in 0 until data.length()) {
                        println(data.getJSONObject(i))
                    }
//                    val data = body?.split("\\|".toRegex())
//                    data.forEach {
//                        val row = it?.split(";".toRegex())
//                        val code = row!![0]
//                        val mess = row!![1]
//                        var type = row!![2]

//                        if (db.testOne(code)) {
//                            val new = message(1, type.toInt(), code, mess)
//                            db.addMessage(new)
//                        }
                }
            }
            runOnUiThread(){

            }
        }.start()
    }
}