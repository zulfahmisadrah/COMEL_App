package com.zulfahmi.comelapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.util.CustomConfirmDialog
import kotlinx.android.synthetic.main.activity_main.*

class RescuerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_rescuer)
        val user = FirebaseAuth.getInstance().currentUser as FirebaseUser
        val username = "Hello, ${user.displayName}"

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
        menu_group.setOnClickListener{ startActivity(Intent(this, DetectionActivity::class.java)) }
    }
}
