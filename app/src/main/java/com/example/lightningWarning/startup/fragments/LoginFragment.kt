package com.example.lightningWarning.startup.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentLoginBinding
import java.lang.ClassCastException


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var onLoginClickListener:OnLoginClickListener

    interface OnLoginClickListener{
        fun onClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginClickListener){
            onLoginClickListener = context
        }else{
            throw ClassCastException(context.toString()
                    + " must implement LoginFragment.onLoginClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        binding.tvForgotPassword.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            onLoginClickListener.onClick()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar!!.hide()
    }
}