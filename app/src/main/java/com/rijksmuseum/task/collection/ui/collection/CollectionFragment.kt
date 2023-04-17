package com.rijksmuseum.task.collection.ui.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParams
import com.rijksmuseum.task.databinding.FragmentCollectionBinding
import com.rijksmuseum.task.util.network.AppLanguage
import com.rijksmuseum.task.util.ui.SearchMenuProvider
import com.rijksmuseum.task.util.ui.SpinnerUtil.bindData
import com.rijksmuseum.task.util.ui.SpinnerUtil.setOnItemSelectedListener
import com.rijksmuseum.task.util.ui.ViewBindingFragment
import com.rijksmuseum.task.util.ui.handleError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CollectionFragment : ViewBindingFragment<FragmentCollectionBinding>() {

    private lateinit var collectionAdapter: CollectionAdapter

    private val viewModel: CollectionViewModel by viewModels()

    private val menuProvider = object : SearchMenuProvider() {
        override fun onTextChanged(query: String) {
            viewModel.search(query)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionAdapter = CollectionAdapter {
            findNavController().navigate(
                CollectionFragmentDirections.actionCollectionFragmentToCollectionDetailFragment(
                    it.title, it.objectNumber, viewModel.state().culture
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
            adapter = collectionAdapter
            setHasFixedSize(true)
        }

        binding.swipeLayout.setOnRefreshListener {
            collectionAdapter.refresh()
        }

        return binding.root
    }

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCollectionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity()
            .addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        initLanguageSpinner()
        initSortSpinner()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResultFlow.collectLatest {
                    collectionAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectionAdapter.loadStateFlow.collectLatest {
                    binding.swipeLayout.isRefreshing = it.refresh is LoadState.Loading
                    if (it.refresh is LoadState.Error)
                        handleError((it.refresh as LoadState.Error).error)
                }
            }
        }
    }

    private fun initLanguageSpinner() {
        with(binding.languageSpinner) {
            bindData(
                AppLanguage.values().map { it.code }
            )
            setOnItemSelectedListener {
                AppLanguage.findByOrdinal(it)?.let { ln ->
                    Log.d(TAG, "On language changed. Ln: $ln")
                    viewModel.language(ln)
                }
            }
        }
    }

    private fun initSortSpinner() {
        with(binding.sortSpinner) {
            bindData(
                CollectionSearchParams.Sort.values().map { it.value }
            )
            setOnItemSelectedListener {
                CollectionSearchParams.Sort.findByOrdinal(it)?.let { sort ->
                    Log.d(TAG, "On sort changed. Sort by: $sort")
                    viewModel.sort(sort)
                }
            }
        }
    }

    companion object {
        private val TAG = CollectionFragment::class.java.simpleName
    }
}