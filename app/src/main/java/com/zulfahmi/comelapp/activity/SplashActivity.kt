package com.zulfahmi.comelapp.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.model.User

class SplashActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)

        database = FirebaseDatabase.getInstance().reference.child("user")

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            user?.let{
                val uid = user.uid
                Log.d("coba", "userid adalaha $uid")

                database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.d(ContentValues.TAG, databaseError.message)
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val currentUser = dataSnapshot.getValue(User::class.java)
                        currentUser?.let{
                            when (it.role) {
                                "general" -> startActivity(Intent(applicationContext, MainActivity::class.java))
                                "rescuer" -> startActivity(Intent(applicationContext, RescuerActivity::class.java))
                                "admin" -> startActivity(Intent(applicationContext, AdminActivity::class.java))
                            }
                            finish()
                        }
                    }

                })
            }
        }else{
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000)
        }
    }
}
