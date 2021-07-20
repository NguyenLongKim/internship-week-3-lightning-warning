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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentProfileBinding
import com.example.lightningWarning.models.UserData
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
                .placeholder(R.drawable.default_avatar)
                .into(view)
        }
    }

    private val viewModel by viewModels<ProfileFragmentViewModel>()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userData: UserData
    private var updateAvatarFlag = false // to indicate that the user just updates avatar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userData = (activity as MainActivity).getUserData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        // set title
        (activity as MainActivity).setToolBarTitle("Profile")

        // listen to navigate to change password fragment
        binding.tvChangePassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }

        // bind user data
        binding.userData = userData

        // put avatar response observer
        viewModel.getPutAvatarResponseLiveData().observe(viewLifecycleOwner, { response ->
            if (response != null && response.status && updateAvatarFlag) {// put avatar successfully
                Toast.makeText(context, "Update avatar successfully", Toast.LENGTH_SHORT).show()
                userData.avatar = response.data.avatar // update avatar url
                binding.userData = userData // update user data for view
            } else if (updateAvatarFlag) {// put avatar failed
                Toast.makeText(context, "Update avatar failed!", Toast.LENGTH_SHORT).show()
            }
            updateAvatarFlag = false
        })

        // listen to process to update avatar
        binding.tvTapToChange.setOnClickListener {
            processToUpDateAvatar()
        }

        return binding.root
    }

    private fun processToUpDateAvatar() {
        val currentStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (currentStoragePermission == PackageManager.PERMISSION_GRANTED) {
            getImageLauncher.launch("image/*")
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val getImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                putAvatar(uri)
            }
        }

    private fun putAvatar(selectedImage: Uri) {
        updateAvatarFlag = true
        val path = RealPathUtil.getRealPath(context, selectedImage)
        val imageFile = File(path)
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData(
            "avatar",
            imageFile.name,
            requestFile
        )
        viewModel.putAvatar(userData.token.token, image)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getImageLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
}