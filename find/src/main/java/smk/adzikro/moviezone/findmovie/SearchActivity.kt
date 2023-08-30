package smk.adzikro.moviezone.findmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.core.data.Resource
import smk.adzikro.moviezone.core.data.source.remote.api.ApiParams
import smk.adzikro.moviezone.core.di.SearchModuleDependencies
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.findmovie.databinding.ActivitySearchBinding
import smk.adzikro.moviezone.ui.adapter.MoviesAdapter
import smk.adzikro.moviezone.ui.detail.DetailActivity
import javax.inject.Inject


class SearchActivity : AppCompatActivity(), MoviesAdapter.OnItemClickCallback {

    private var _binding : ActivitySearchBinding? = null
    private lateinit var movieAdapter: MoviesAdapter
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: SearchViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSearchComponen.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    SearchModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isOnLoading(false)
        movieAdapter = MoviesAdapter(this)
        binding.apply {
            listMovie.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = movieAdapter
            }
        }
        setupView()
    }

    private fun setupView() {
        val data = intent.getStringExtra(SEARCH)
        binding.apply {
            viewEmpty.root.visibility = View.VISIBLE
            findMovie.getToolbar().inflateMenu(R.menu.home_menu)
            findMovie.toggleHideOnScroll(false)
            findMovie.setupMenu()
            if(!data.isNullOrEmpty()){
                searchMovie(data)
            }

            findMovie.onSearchTextChangedListener = { text ->
                if(text.isNotEmpty()) {
                    searchMovie(text)
                }
            }
            findMovie.getToolbar().menu.apply {
                findItem(R.id.action_setting).isVisible = false
            }
            findMovie.getToolbar().setOnMenuItemClickListener { menuItem ->


                when (menuItem.itemId) {
                    R.id.action_logout -> finish()
                    else -> return@setOnMenuItemClickListener false
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun searchMovie(query: String) {
        viewModel.getParams().observe(this) { params ->
            params.let {
                val hashMap = HashMap<String, String>()
                hashMap[ApiParams.SORT_BY] = it.sortBy!!
                hashMap[ApiParams.INCLUDE_ADULT] = it.isAdult.toString()
                hashMap[ApiParams.QUERY] = query
                viewModel.getMovie(hashMap).observe(this, movieObserver)
            }
        }
    }

    private val movieObserver = Observer<Resource<List<Movie>>> {
        if (it != null) {
            when (it) {
                is Resource.Loading -> isOnLoading(true)
                is Resource.Success -> {
                    isOnLoading(false)
                    movieAdapter.differ.submitList(it.data)
                    binding.viewEmpty.root.visibility = if (it.data?.isNotEmpty()!!) View.GONE else View.VISIBLE
                }
                is Resource.Empty ->binding.viewEmpty.root.visibility = View.VISIBLE
                is Resource.Error -> {
                    isOnLoading(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isOnLoading(isLoading: Boolean) {
        binding.apply {
            shimmer.isVisible = isLoading
        }
    }


    override fun onItemClicked(
        movie: Movie?,
        image: ImageView,
        desc: TextView,
        name: TextView,
        date: TextView,
        favo : AppCompatImageView,
        valueRating : AppCompatRatingBar
    ) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair(desc, "description"),
            Pair(image, "image"),
            Pair(name, "name"),
            Pair(date, "date"),
            Pair(valueRating, "rating"),
            Pair(favo, "favo")
        )
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL, movie)
        startActivity(intent, options.toBundle())
    }
    override fun onDestroy() {
        binding.listMovie.adapter = null
        super.onDestroy()
        movieAdapter.differ.submitList(null)
        _binding = null
    }
    companion object {
        const val SEARCH = "search"
    }
}