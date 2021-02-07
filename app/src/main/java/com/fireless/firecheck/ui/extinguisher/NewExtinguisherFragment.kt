package com.fireless.firecheck.ui.extinguisher

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Slide
import com.fireless.firecheck.R
import com.fireless.firecheck.databinding.FragmentNewExtinguisherBinding
import com.google.android.material.transition.MaterialContainerTransform

@Suppress("DEPRECATION")
class NewExtinguisherFragment : Fragment() {

    private val viewModel: NewExtinguisherViewModel by lazy {
        ViewModelProvider(this).get(NewExtinguisherViewModel::class.java)
    }

    private lateinit var binding: FragmentNewExtinguisherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewExtinguisherBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.run {

            // Set transitions here so we are able to access Fragment's binding views.
            enterTransition = MaterialContainerTransform().apply {
                // Manually add the Views to be shared since this is not a standard Fragment to
                // Fragment shared element transition.
                startView = requireActivity().findViewById(R.id.fab)
            }
            returnTransition = Slide().apply {
                duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard(requireActivity())

    }

    private fun hideKeyboard(activity: Activity) {
        val inputManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}