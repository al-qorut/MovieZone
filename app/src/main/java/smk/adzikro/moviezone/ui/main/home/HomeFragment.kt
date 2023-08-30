package smk.adzikro.moviezone.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.core.data.source.remote.api.ApiParams.INCLUDE_ADULT
import smk.adzikro.moviezone.core.data.source.remote.api.ApiParams.LANG
import smk.adzikro.moviezone.core.data.source.remote.api.ApiParams.SORT_BY
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.utils.debug
import smk.adzikro.moviezone.databinding.FragmentHomeBinding
import smk.adzikro.moviezone.ui.adapter.LoadingStateAdapter
import smk.adzikro.moviezone.ui.adapter.MovieAdapter
import smk.adzikro.moviezone.ui.detail.DetailActivity


@AndroidEntryPoint
class HomeFragment : Fragment(), MovieAdapter.OnItemClickCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter
    private var path = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = getString(R.string.img_path, "w500")
        setUpView()
    }


    private fun setUpView() {
        isOnLoading(true)
        movieAdapter = MovieAdapter(requireContext(), this)
        homeViewModel.getParams().observe(viewLifecycleOwner) { params ->
            params.let {
                val hashMap = HashMap<String, String>()
                hashMap[SORT_BY] = it.sortBy!!
                hashMap[INCLUDE_ADULT] = it.isAdult.toString()
                hashMap[LANG] = it.language!!
                path = getString(R.string.img_path, it.imageQuality)
                homeViewModel.getMovieAll(hashMap).observe(viewLifecycleOwner, movieObserver)
            }
        }

        binding?.apply {
            rvHome.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = movieAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        movieAdapter.retry()
                    })
            }
        }
    }

    private val movieObserver = Observer<PagingData<Movie>> {
        isOnLoading(false)
        movieAdapter.submitData(lifecycle, it)
    }

    private fun isOnLoading(isLoading: Boolean) {
        binding?.apply {
            shimmer.isVisible = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieAdapter.submitData(lifecycle, PagingData.empty())
    }
    private fun isFavorite(data: Movie?) {
        if(data==null)return
        data.favorite = !data.favorite
        homeViewModel.favoriteMovie(data)
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
            requireActivity(),
            Pair(desc, "description"),
            Pair(image, "image"),
            Pair(name, "name"),
            Pair(date, "date"),
            Pair(valueRating, "rating"),
            Pair(favo, "favo")
        )
        favo.setOnClickListener {
            debug("click favorite")
            isFavorite(movie)
        }
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL, movie)
        startActivity(intent, options.toBundle())
    }
}
