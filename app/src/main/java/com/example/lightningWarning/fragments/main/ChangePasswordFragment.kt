package com.example.lightningWarning.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentChangePasswordBinding
import com.example.lightningWarning.models.UserData
import com.example.lightningWarning.viewmodels.ProfileFragmentViewModel

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel by navGraphViewModels<ProfileFragmentViewModel>(R.id.profileFragment)
    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = (activity as MainActivity).getUserData()
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_change_password,container,false)

        binding.btnUpdate.setOnClickListener {
            viewModel.changePassword(
                userData.token.token,
                binding.tvNewPassword.text.toString(),
                binding.tvConfirmNewPassword.text.toString(),
                binding.tvOldPassword.text.toString()
            )
        }

        viewModel.getChangePasswordResponseLiveData().observe(viewLifecycleOwner,{response->
            if (response.status){
                viewModel.reSignIn(
                    userData.email,
                    binding.tvNewPassword.text.toString()
                )
            }else{
                Toast.makeText(context,"Change password failed!",Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getReSignInResponseLiveData().observe(viewLifecycleOwner,{response->
            if (response.status){
                Toast.makeText(context,"Re sign in successfully!",Toast.LENGTH_SHORT).show()
                (activity as MainActivity).setUserData(response.data!!)
            }else{
                Toast.makeText(context,"Re sign in failed!",Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}