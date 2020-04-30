package com.zulfahmi.comelapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zulfahmi.comelapp.model.Detection
import com.zulfahmi.comelapp.model.User
import com.zulfahmi.comelapp.util.Constants

class MainViewModel: ViewModel(){
    private val listDetection: MutableLiveData<ArrayList<Detection>> = MutableLiveData()
    private val listUserInfo: MutableLiveData<User> = MutableLiveData()

    private val database = FirebaseDatabase.getInstance().reference

    fun getAllDetection(): LiveData<ArrayList<Detection>>{
        database.child(Constants.FIREBASE_DETECTIONS).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("error", "loadItem:onCancelled", p0.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listItems: ArrayList<Detection> = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    Log.d("coba", postSnapshot.toString())
                    val detection = postSnapshot.getValue(Detection::class.java) as Detection
                    detection.date = postSnapshot.key as String
                    listItems.add(detection)
                }
                listDetection.postValue(listItems)
            }

        })
        return listDetection
    }

    fun getUserInfo(uid: String): LiveData<User>{
        database.child("${Constants.FIREBASE_USER}/$uid").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("error", "loadItem:onCancelled", p0.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: User = dataSnapshot.getValue(User::class.java) as User
                Log.d("coba", dataSnapshot.toString())
                listUserInfo.postValue(user)
            }
        })
        return listUserInfo
    }
}
