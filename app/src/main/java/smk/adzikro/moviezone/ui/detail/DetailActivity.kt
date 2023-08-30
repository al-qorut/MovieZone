package smk.adzikro.moviezone.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.BuildConfig
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.core.data.Resource
import smk.adzikro.moviezone.core.domain.model.Actor
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.utils.options
import smk.adzikro.moviezone.databinding.ActivityDetailMovieBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var castAdapter : ActorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpView()
    }

    private fun setUpView() {
        castAdapter = ActorAdapter()
        @Suppress("DEPRECATION")
        val data = intent.getParcelableExtra<Movie>(DETAIL)
        data?:return
        viewModel.getActor(data.id).observe(this, actorObserver)
        supportActionBar?.title = data.title
        binding.apply {
            textOverview.text = data.overview
            textReleaseDate.text = data.release_date
            txRating.text = data.vote_average.toString()
            val rating = (data.vote_average?.toFloat() ?: 0f) * 0.5f
            valueRating.rating = rating
            val fabicon =
                if (data.favorite) ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_white_24dp
                ) else ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.ic_favorite_border_white_24dp
                )
            buttonFavorite.setImageDrawable(fabicon)
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMG_ORIGINAL.plus(data.backdrop_path))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageBackdrop)
            buttonFavorite.setOnClickListener {
                isFavorite(data)
            }

            recyclerCast.apply {
                setHasFixedSize(true)
                adapter = castAdapter
            }
        }
    }

    private val actorObserver = Observer<Resource<List<Actor>>>{
        if(it !=null){
            when(it){
                is Resource.Success -> castAdapter.differ.submitList(it.data)
                is Resource.Loading -> {}
                is Resource.Empty ->{}
                is Resource.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFavorite(data: Movie) {
        data.favorite = !data.favorite
        val fabicon = if (data.favorite)
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        binding.buttonFavorite.setImageDrawable(fabicon)
        viewModel.favoriteMovie(data)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_white_ios_24)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onDestroy() {
        binding.recyclerCast.adapter = null
        super.onDestroy()
        castAdapter.differ.submitList(null)
        _binding = null
    }
    companion object {
        const val DETAIL = "detail"
    }
}