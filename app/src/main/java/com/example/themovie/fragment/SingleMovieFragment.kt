package com.example.themovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.themovie.R
import com.example.themovie.databinding.FragmentSingleMovieBinding
import com.example.themovie.model.api.ApiHelper
import com.example.themovie.model.api.RetrofitBuilder
import com.example.themovie.utils.Status
import com.example.themovie.viewmodel.MovieSingleViewModel
import com.example.themovie.viewmodel.ViewModelFactory

/**
 * Created by aizat on 27/4/2021
 */
class SingleMovieFragment : Fragment() {

    private lateinit var viewModel: MovieSingleViewModel

    private var _binding: FragmentSingleMovieBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSingleMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.movie_details)

        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), requireArguments())
        ).get(MovieSingleViewModel::class.java)
    }

    private fun setupUI() {
        binding.viewmodel = viewModel
    }

    private fun setupObservers() {
        viewModel.getMovie().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressbar.visibility = View.GONE
                        resource.data?.let { movie -> viewModel.movieLive.postValue(movie) }
                    }
                    Status.ERROR -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}