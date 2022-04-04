package com.example.numberguesser.activities.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.example.numberguesser.DB.Contract.Contract
import com.example.numberguesser.DB.User.User
import com.example.numberguesser.R
import com.example.numberguesser.activities.mainactivity.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var inputUsername : EditText
    lateinit var inputPassword : EditText
    lateinit var switchRegister : SwitchCompat
    lateinit var buttonLogin : Button
    lateinit var dbHelper : Contract.UserDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = Contract.UserDbHelper(applicationContext)

        inputUsername = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        switchRegister = findViewById(R.id.switchRegister)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener(){
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()
            val register = switchRegister.isChecked

            if(username.isEmpty() || password.isEmpty()) {
                showToast("Wartości nie mogą być puste!")
                return@setOnClickListener
            }

            if(register) {
                val user = dbHelper.getUserByUsername(username)
                if(user.username.isEmpty()) {
                    //user does not exist - create user
                    val newUser = User()
                    newUser.username = username
                    newUser.password = password
                    dbHelper.addUser(newUser)
                    saveCurrentUserToMemory(newUser.username)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Użytkownik już istnieje")
                }
            } else {
                val user = dbHelper.getUserByUsername(username)
                when {
                    user.username.isEmpty() -> {
                        showToast("Podany użytkownik nie istnieje!")
                    }
                    user.password != password -> {
                        showToast("Hasło jest niepoprawne!")
                    }
                    else -> {
                        saveCurrentUserToMemory(user.username)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

    }

    override fun onDestroy(){
        dbHelper.close()
        super.onDestroy()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun saveCurrentUserToMemory(username: String){
        val shared = getSharedPreferences("com.example.numberguesser.shared",0)
        val edit = shared.edit()
        edit.putString("current_user", username)
        edit.apply()
    }

    fun readCurrentUserFromMemory(): String? {
        val shared = getSharedPreferences("com.example.numberguesser.shared",0)
        return shared.getString("current_user", "")
    }
}