package smk.adzikro.moviezone.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.utils.debug
import smk.adzikro.moviezone.databinding.FragmentHomeBinding
import smk.adzikro.moviezone.ui.adapter.FavoriteAdapter
import smk.adzikro.moviezone.ui.detail.DetailActivity


@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }


    private fun setUpView() {
        isOnLoading(true)
        val favoAdapter = FavoriteAdapter(this)
        favoriteViewModel.getFavoMovie().observe(viewLifecycleOwner) {
            isOnLoading(false)
            favoAdapter.differ.submitList(it)
            binding.viewEmpties.root.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            debug("panggil mang")
        }
        with(binding.rvHome) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = favoAdapter
       }
    }


    private fun isOnLoading(isLoading: Boolean) {
        binding.apply {
            shimmer.isVisible = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL, movie)
        startActivity(intent, options.toBundle())
    }

}