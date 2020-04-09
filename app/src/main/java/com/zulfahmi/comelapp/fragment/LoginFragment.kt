package com.zulfahmi.comelapp.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction

import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_password.transformationMethod = PasswordTransformationMethod()

        btn_login.setOnClickListener { startActivity(Intent(context, MainActivity::class.java)) }
        tvclick_lupa_password.setOnClickListener {
            replaceFragmentWithAnimationNext(ForgotPasswordFragment(), "fragment_forgot")
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
