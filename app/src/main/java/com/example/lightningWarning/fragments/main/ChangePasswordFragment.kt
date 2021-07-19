package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentChangePasswordBinding
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.viewmodels.ChangePasswordFragmentViewModel
import com.example.lightningWarning.viewmodels.ProfileFragmentViewModel

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordFragmentViewModel>()
    private lateinit var userData: UserData
    private lateinit var newPassword: String
    private lateinit var passwordConfirmation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = (activity as MainActivity).getUserData()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        "Change Password".also {
            (activity as AppCompatActivity)
                .findViewById<TextView>(R.id.toolbar_title)
                .text = it
        }

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)

        binding.btnUpdate.setOnClickListener {
            newPassword = binding.etNewPassword.text.toString()
            passwordConfirmation = binding.etConfirmNewPassword.text.toString()
            viewModel.changePassword(
                userData.token.token,
                newPassword,
                passwordConfirmation,
                binding.etOldPassword.text.toString()
            )
        }

        viewModel.getChangePasswordResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response != null && response.status) {
                binding.etNewPassword.text.clear()
                binding.etConfirmNewPassword.text.clear()
                binding.etOldPassword.text.clear()
                viewModel.reSignIn(
                    userData.email,
                    newPassword
                )
                Toast.makeText(context, "Change password successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Change password failed!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getReSignInResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response != null && response.status) {
                Toast.makeText(context, "Re sign in successfully", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).setUserData(response.data!!)
            } else {
                Toast.makeText(context, "Re sign in failed!", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}