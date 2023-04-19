package com.rijksmuseum.task.collection.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rijksmuseum.task.R
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.collection.presentation.detail.model.CollectionDetailViewData
import com.rijksmuseum.task.collection.presentation.util.circularProgressDrawable
import com.rijksmuseum.task.databinding.FragmentCollectionDetailBinding
import com.rijksmuseum.task.util.presentation.ViewBindingFragment
import com.rijksmuseum.task.util.presentation.handleError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CollectionDetailFragment : ViewBindingFragment<FragmentCollectionDetailBinding>() {

    private val args: CollectionDetailFragmentArgs by navArgs()

    private val viewModel: CollectionDetailViewModel by viewModels()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCollectionDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detail.collectLatest {
                    when {
                        it.isSuccess() -> handleResult(it.toData())
                        else -> handleError(it.toException())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loader.collectLatest {
                    binding.progressbar.isVisible = it
                }
            }
        }

        viewModel.loadDetail(
            CollectionDetailParamsModel(
                args.culture, args.number
            )
        )
    }

    private fun handleResult(response: CollectionDetailViewData) {
        binding.description.text = response.description
        binding.title.text = response.title
        binding.author.text = getString(R.string.author_param, response.author)

        response.image?.let {
            Glide.with(binding.image)
                .load(it)
                .placeholder(requireContext().circularProgressDrawable())
                .into(binding.image)
        }
    }
}