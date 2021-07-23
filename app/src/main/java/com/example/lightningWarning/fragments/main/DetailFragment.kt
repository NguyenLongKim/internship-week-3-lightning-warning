package com.example.lightningWarning.fragments.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.lightningWarning.MainActivity
import com.example.lightningWarning.R
import com.example.lightningWarning.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // set toolbar title
        (activity as MainActivity).setToolBarTitle("Details")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        val sdf = SimpleDateFormat("dd MMM, h:mm a", Locale.ENGLISH)
        binding.tvTime.text = sdf.format(arguments?.getLong("created_time")?.times(1000))

        binding.tvTitle.text = arguments?.getString("title")

        binding.tvDescription.text = arguments?.getString("description")

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }
}