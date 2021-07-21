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
import java.time.chrono.MinguoChronology

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel by viewModels<ChangePasswordFragmentViewModel>()
    private lateinit var newPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // set toolbar title
        (activity as MainActivity).setToolBarTitle("Change Password")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_change_password,
            container,
            false
        )

        // listen to request change password
        binding.btnUpdate.setOnClickListener {
            newPassword = binding.etNewPassword.text.toString() // save new password
            viewModel.changePassword(
                (activity as MainActivity).getToken(),
                newPassword,
                binding.etConfirmNewPassword.text.toString(),
                binding.etOldPassword.text.toString()
            )
        }

        // change password response observer
        viewModel.getChangePasswordResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response != null && response.status) { // if change password successfully
                binding.etNewPassword.text.clear()
                binding.etConfirmNewPassword.text.clear()
                binding.etOldPassword.text.clear()
                viewModel.reSignIn(
                    (activity as MainActivity).getUserData().email,
                    newPassword
                )
                Toast.makeText(context, "Change password successfully", Toast.LENGTH_SHORT).show()
            }
        })

        // re-signIn response observer
        viewModel.getReSignInResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response != null && response.status) {
                Toast.makeText(context, "Re sign in successfully", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).upDateUserData(response.data!!) // update user data
            }
        })

        // error response observer
        viewModel.getErrorResponseLiveData().observe(viewLifecycleOwner, { response ->
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}