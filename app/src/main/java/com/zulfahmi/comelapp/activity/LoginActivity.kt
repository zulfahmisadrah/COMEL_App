package com.zulfahmi.comelapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                LoginFragment(), "fragment_login")
            .commit()
    }
}
