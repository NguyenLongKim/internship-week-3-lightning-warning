package com.example.lightningWarning.fragments.startup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentLoginBinding
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.viewmodels.SignInActivityViewModel
import java.lang.ClassCastException


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<SignInActivityViewModel>()
    private lateinit var listener: OnLoginSuccessListener


    interface OnLoginSuccessListener{
        fun onLoginSuccess(userData:UserData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginSuccessListener){
            listener = context
        }else{
            throw ClassCastException(context.toString()
            +"must implement LoginFragment.OnLoginSuccessListener")
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
            viewModel.signIn(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        viewModel.getSignInResponseLiveData().observe(viewLifecycleOwner,{signInResponse->
            if (signInResponse!=null) {
                listener.onLoginSuccess(signInResponse.data!!)
            }else{
                Toast.makeText(context,"Invalid Email or Password!",Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.hide()
    }
}