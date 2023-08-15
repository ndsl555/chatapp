package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.net.Authenticator

class Login : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()

        edtEmail =findViewById(R.id.edt_email)
        editPassword=findViewById(R.id.edt_password)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=editPassword.text.toString()
            if (!email.isNullOrBlank()&&!password.isNullOrBlank()) {
                logIn(email, password)
            }
        }
    }
    private fun logIn(email:String,password:String){
        //login
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //logging user
                    val intent= Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login,"使用者不存在", Toast.LENGTH_SHORT).show()
                }
            }
    }
}