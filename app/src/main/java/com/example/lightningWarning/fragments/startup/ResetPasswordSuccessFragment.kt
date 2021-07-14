package com.example.lightningWarning.fragments.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lightningWarning.R

class ResetPasswordSuccessFragment : Fragment() {
    private val args: ResetPasswordSuccessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_password_success, container, false)
        view.findViewById<TextView>(R.id.tv_message).text = args.email
        view.findViewById<Button>(R.id.btn_back_to_login).setOnClickListener {
            val action =
                ResetPasswordSuccessFragmentDirections.actionResetPasswordSuccessFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity
        val toolbar = activity.supportActionBar
        "Success".also { activity.findViewById<TextView>(R.id.toolbar_title).text = it }
        toolbar?.show()
    }
}