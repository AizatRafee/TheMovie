package com.example.themovie.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovie.R
import com.example.themovie.adapter.MovieAdapter
import com.example.themovie.databinding.FragmentHomeBinding
import com.example.themovie.model.api.ApiHelper
import com.example.themovie.model.api.RetrofitBuilder
import com.example.themovie.model.entities.Movie
import com.example.themovie.utils.Status
import com.example.themovie.viewmodel.MovieListViewModel
import com.example.themovie.viewmodel.MovieSingleViewModel.Companion.MOVIE_ID
import com.example.themovie.viewmodel.ViewModelFactory


/**
 * Created by aizat on 27/4/2021
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var loading = false

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.movie_list)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), arguments)
        ).get(MovieListViewModel::class.java)
    }

    private fun setupUI() {

        movieAdapter = MovieAdapter(arrayListOf())
        movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = movieAdapter
    }

    private fun setupObservers() {

        loadMovie()

        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.rvMovies.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    Log.d("Yeahh", "yeahh")
                    loadMovie()
                }
            }
        })

        binding.srlRefresh.setOnRefreshListener {
            viewModel.refreshMovieList()
            loadMovie()
        }

        movieAdapter.clickEvent.observe(viewLifecycleOwner, Observer {
            val bundle = Bundle().apply {
                putInt(MOVIE_ID, it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_singleMovieFragment, bundle)
        })
    }

    private fun loadMovie() {
        viewModel.getMovies().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading = false
                        if (binding.srlRefresh.isRefreshing) {
                            binding.srlRefresh.isRefreshing = false
                        }
                        resource.data?.let { moviePage ->
                            viewModel.updateMovieList(moviePage)
                            retrieveList(
                                viewModel.movies,
                                viewModel.movies.size - moviePage.results.size
                            )
                        }
                    }
                    Status.ERROR -> {
                        loading = false
                        if (binding.srlRefresh.isRefreshing) {
                            binding.srlRefresh.isRefreshing = false
                        }
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        loading = true
                    }
                }

                if (viewModel.movies.isNullOrEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                }
            }
        })
    }

    private fun retrieveList(movieList: List<Movie>, lastSize: Int) {
        movieAdapter.apply {
            addMovie(movieList)
            if (lastSize <=0) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(lastSize, movieList.size - lastSize)
                binding.rvMovies.smoothScrollBy(0, 50)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}