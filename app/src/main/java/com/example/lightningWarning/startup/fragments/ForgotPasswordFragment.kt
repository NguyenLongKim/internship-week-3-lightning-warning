package com.example.lightningWarning.startup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.R

class ForgotPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forget_password, container, false)
        view.findViewById<Button>(R.id.btn_send).setOnClickListener {
            val action = ForgotPasswordFragmentDirections
                .actionForgotPasswordFragmentToResetPasswordSuccessFragment("abc@gmail.com")
            findNavController().navigate(action)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity
        "Forgot Password".also { activity.findViewById<TextView>(R.id.toolbar_title).text = it }
        val toolbar = activity.supportActionBar
        toolbar!!.show()
    }
}