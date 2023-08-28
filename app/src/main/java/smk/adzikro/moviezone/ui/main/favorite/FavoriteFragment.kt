package smk.adzikro.moviezone.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.databinding.FragmentHomeBinding
import smk.adzikro.moviezone.ui.adapter.FavoriteAdapter
import smk.adzikro.moviezone.ui.detail.DetailActivity

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var favoAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpView()
    }

    private fun setUpToolbar() {
        (activity as AppCompatActivity).apply {
            //setSupportActionBar(binding?.toolbar)
        }
    }



    private fun setUpView() {
        isOnLoading(true)
        favoriteViewModel.getFavoMovie().observe(viewLifecycleOwner, movieObserver)
        favoAdapter = FavoriteAdapter(this)
        binding?.rvHome?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = favoAdapter
       }
    }

    private val movieObserver = Observer<List<Movie>> {
        isOnLoading(false)
        favoAdapter.differ.submitList(it)
    }

    private fun isOnLoading(isLoading: Boolean) {
        binding?.apply {
            shimmer.isVisible = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favoAdapter.differ.submitList(null)
    }

    override fun onItemClicked(
        movie: Movie?, image: ImageView, desc: TextView, name: TextView, date: TextView
    ) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            Pair(desc, "description"),
            Pair(image, "image"),
            Pair(name, "name"),
            Pair(date, "date")
        )
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL, movie)
        startActivity(intent, options.toBundle())
    }
}