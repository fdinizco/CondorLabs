package com.example.androidtechnicaltest.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.androidtechnicaltest.R
import com.example.androidtechnicaltest.ui.base.BaseFragment
import com.example.androidtechnicaltest.extension.toGone
import com.example.androidtechnicaltest.extension.toVisible
import com.example.androidtechnicaltest.databinding.LoadingFragmentBinding
import com.example.androidtechnicaltest.ui.main.MainViewModel

class LoadingFragment : BaseFragment<LoadingViewModel>(R.layout.loading_fragment, LoadingViewModel::class) {

    private lateinit var stateObserver: Observer<LoadingState>
    private lateinit var bindingLoading: LoadingFragmentBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = LoadingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingLoading = LoadingFragmentBinding.inflate(inflater, container, false)
        return bindingLoading.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(isVisible = false)
        setupEventObservers()
    }

    private fun setupEventObservers() {
        stateObserver = Observer {
            it ?: return@Observer
            when (it) {
                is LoadingState.OnSuccess -> setLoadingState(isLoading = false)
                is LoadingState.OnRetrieving -> setLoadingState(isLoading = true)
                is LoadingState.OnError -> setLoadingState(isLoading = false)
            }
        }
        mainViewModel.loadingState.observe(viewLifecycleOwner, stateObserver)
    }

    private fun setLoadingState(isLoading : Boolean = false) = when {
        isLoading -> {
            bindingLoading.progressBar.toVisible(animate = false)
            bindingLoading.loadingTextView.toVisible(animate = false)
        }
        else -> {
            bindingLoading.progressBar.toGone(animate = false)
            bindingLoading.loadingTextView.toGone(animate = false)
        }
    }

}