package com.rba.hilttest.presentation.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rba.hilttest.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter =
            MovieAdapter { showToast(it.title) }

        viewModel.viewState.observe(viewLifecycleOwner) { updateUI(it) }

        binding.movieRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.movieRecyclerView.adapter = movieAdapter

        binding.movieSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getPopularMovies()
        }

        viewModel.getPopularMovies()
    }

    private fun updateUI(viewState: MovieViewModel.ViewState) {
        when (viewState) {
            is MovieViewModel.ViewState.ShowLoading -> {
                binding.movieSwipeRefreshLayout.isRefreshing = true
            }

            is MovieViewModel.ViewState.HideLoading -> {
                binding.movieSwipeRefreshLayout.isRefreshing = false
            }

            is MovieViewModel.ViewState.Data -> {
                movieAdapter.submitList(viewState.data)
            }

            is MovieViewModel.ViewState.Error -> {
                showToast(viewState.message)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
