package com.zulfahmi.comelapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.util.CustomConfirmDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_main)

        btn_logout.setOnClickListener {
            CustomConfirmDialog(this, "LOGOUT", "Are you sure want to logout?") {
                startActivity(Intent(this, LoginActivity::class.java))
            }.show()
        }

        menu_info.setOnClickListener{ startActivity(Intent(this, MyInfoActivity::class.java)) }
        menu_group.setOnClickListener{ startActivity(Intent(this, MyGroupActivity::class.java)) }
        menu_message.setOnClickListener{ startActivity(Intent(this, MessageActivity::class.java)) }

    }
}
