package com.example.lightningWarning.fragments.main

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentProfileBinding
import com.example.lightningWarning.utils.RealPathUtil
import com.example.lightningWarning.viewmodels.ProfileFragmentViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.jar.Manifest

class ProfileFragment : Fragment() {
    companion object {
        @JvmStatic
        @BindingAdapter("avatarUrl")
        fun loadAvatar(view: ImageView, avatarUrl: String) {
            Glide.with(view.context)
                .load(avatarUrl)
                .override(300, 300)
                .into(view)
        }
    }

    private val viewModel by navGraphViewModels<ProfileFragmentViewModel>(R.id.profileFragment)
    private lateinit var binding: FragmentProfileBinding
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = (activity as MainActivity).getToken()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        // set title
        "My Profile".also {
            (activity as AppCompatActivity)
                .findViewById<TextView>(R.id.toolbar_title)
                .text = it
        }

        // listen to navigate to change password fragment
        binding.tvChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }

        val userData = (activity as MainActivity).getUserData()
        binding.userData = userData

        viewModel.getPutAvatarResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response.status) {
                Toast.makeText(context, "Update avatar successfully", Toast.LENGTH_SHORT).show()
                userData.avatar = response.data.avatar
                binding.userData = userData
            } else {
                Toast.makeText(context, "Update avatar failed!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.tvTapToChange.setOnClickListener {
            processToUpDateAvatar()
        }

        return binding.root
    }

    private fun putAvatar(selectedImage: Uri) {
        val path = RealPathUtil.getRealPath(context, selectedImage)
        val imageFile = File(path)
        val requestFile = imageFile
            .asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData(
            "avatar",
            imageFile.name,
            requestFile
        )
        viewModel.putAvatar(token, image)
    }

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                putAvatar(uri)
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getImageLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }

    private fun processToUpDateAvatar() {
        val currentPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (currentPermission == PackageManager.PERMISSION_GRANTED) {
            getImageLauncher.launch("image/*")
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}