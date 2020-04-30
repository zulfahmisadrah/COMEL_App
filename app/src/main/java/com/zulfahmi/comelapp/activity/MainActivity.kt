package com.zulfahmi.comelapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.util.Constants
import com.zulfahmi.comelapp.util.CustomConfirmDialog
import com.zulfahmi.comelapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ROLE = "extra_role"
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val user = FirebaseAuth.getInstance().currentUser as FirebaseUser
        mainViewModel.getUserInfo(user.uid).observe(this, Observer {
            val username = it.username
            tv_username.text = it.userId
            val strUsername = "Hello, $username"
            tv_hello_name.text = strUsername
        })




        btn_logout.setOnClickListener {
            CustomConfirmDialog(this, "LOGOUT", "Are you sure want to logout?") {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.show()
        }

        menu_info.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_UID, user.uid)
            startActivity(intent)
        }
        menu_group.setOnClickListener{ startActivity(Intent(this, MyGroupActivity::class.java)) }
        menu_notification.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_UID, user.uid)
            startActivity(intent)
        }

    }
}
