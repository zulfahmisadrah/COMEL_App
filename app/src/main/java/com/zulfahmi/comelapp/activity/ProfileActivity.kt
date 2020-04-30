package com.zulfahmi.comelapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.util.Constants
import com.zulfahmi.comelapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_UID = "extra_uid"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Profile"

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val uid = intent.extras!!.getString(EXTRA_UID) as String

        database = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(uid)

        mainViewModel.getUserInfo(uid).observe(this, Observer {
            tv_user_id.text = it.userId
            tv_username.text = it.username
            tv_name.text = it.name
            tv_email.text = it.email
            tv_phonenumber.text = it.phonenumber
            tv_address.text = it.address

        })

        btn_edit.setOnClickListener{

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
