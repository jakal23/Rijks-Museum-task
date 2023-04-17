package com.rijksmuseum.task.util.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container)
        return binding.root
    }

    abstract fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T?

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}