package com.zulfahmi.comelapp.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.activity.AdminActivity
import com.zulfahmi.comelapp.activity.MainActivity
import com.zulfahmi.comelapp.activity.RescuerActivity
import com.zulfahmi.comelapp.model.User
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("user")

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_password.transformationMethod = PasswordTransformationMethod()

        btn_login.setOnClickListener {
            val email: String
            if (input_username.text.toString() == "") {
                input_username.error = " Username is required"
                return@setOnClickListener
            }else{
                val inputUsernameEmail = input_username.text!!.contains("@")
                email = if (inputUsernameEmail)
                    input_username.text.toString().trim()
                else
                    input_username.text.toString().trim() + "@gmail.com"
            }


            val password = input_password.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                input_username.error = " Username is required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)){
                input_password.error = " Password is required"
                return@setOnClickListener
            }

            loginUser(email , password)
        }

        tvclick_lupa_password.setOnClickListener {
            replaceFragmentWithAnimationNext(ForgotPasswordFragment(), "fragment_forgot")
        }

    }

    private fun loginUser(email: String, password: String) {
        progressbar.visibility = View.VISIBLE
        activity?.let{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it){task ->
                    if (task.isSuccessful) {
                        progressbar.visibility = View.INVISIBLE
                        checkRole()
                    }else{
                        Toast.makeText(activity, "Username/password invalid", Toast.LENGTH_SHORT).show()
                    }
                    progressbar.visibility = View.INVISIBLE
                }
        }
    }

    private fun checkRole() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = user.uid
            Log.d(ContentValues.TAG, "userid adalah $uid")
            database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentUser = dataSnapshot.getValue(User::class.java)
                    currentUser?.let {
                        when(it.role) {
                            "general" -> startActivity(Intent(context , MainActivity::class.java))
                            "rescuer" -> startActivity(Intent(context , RescuerActivity::class.java))
                            "admin" -> startActivity(Intent(context, AdminActivity::class.java))
                        }
                        activity!!.finish()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(ContentValues.TAG, databaseError.message)
                }
            })

        }

    }

    private fun replaceFragmentWithAnimationNext(fragment: Fragment, tag: String){
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left
        )
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

}
