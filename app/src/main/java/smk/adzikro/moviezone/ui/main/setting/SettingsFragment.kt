package smk.adzikro.moviezone.ui.main.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.core.data.source.local.datastore.Params
import smk.adzikro.moviezone.databinding.FragmentSettingsBinding
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding
    private val setViewModel by viewModels<SettingsViewModel>()
    private var image_quality = "w500"
    private var sort_by = "popularity.desc"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpToolbar()
    }
    private val paramObserver = Observer<Params>{
        when(it.SortBy){
            "popularity.desc" -> binding?.srPopularity?.isChecked
            "revenue.desc" -> binding?.srRevenue?.isChecked
            "vote_average.desc" -> binding?.srVote?.isChecked
            "release_date.desc" -> binding?.srRelease?.isChecked
        }
        when(it.ImageQuality){
            "w342" ->binding?.iqSmall?.isChecked
            "w500" ->binding?.iqMedium?.isChecked
            "w780" ->binding?.iqLarge?.isChecked
        }
        binding?.adult?.isChecked = it.isAdult!!
    }

    private fun setUpView() {
        when (Locale.getDefault().language) {
            "id" -> setImageEnd(R.drawable.ic_flag_id)
            "en" -> setImageEnd(R.drawable.ic_flag_us)
        }
        setViewModel.getParams().observe(viewLifecycleOwner, paramObserver)
        binding?.apply {
            tvLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            imageQuality.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.iq_small -> image_quality = "w342"
                    R.id.iq_medium -> image_quality = "w500"
                    R.id.iq_large -> image_quality = "w780"
                }
            }
            selectSort.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.sr_popularity -> sort_by = "popularity.desc"
                    R.id.sr_revenue -> sort_by = "revenue.desc"
                    R.id.sr_vote -> sort_by = "vote_average.desc"
                    R.id.sr_release ->sort_by = "release_date.desc"
                }
            }
        }
    }
    private fun saveParam(){
        var params = Params(
            binding?.adult?.isChecked,
            sort_by,
            image_quality,
            Locale.getDefault().language
        )
        setViewModel.setParams(params)
        setViewModel.deleteAll()
        Toast.makeText(context,getString(R.string.apply_param), Toast.LENGTH_SHORT).show()
    }

    private fun setUpToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.settings_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            when (menuItem.itemId) {
                /*R.id.action_logout -> {
                    activity?.finish()
                    true
                } */
                R.id.action_apply -> {
                    saveParam()
                    true
                }
                else -> false
            }
    }
    private fun showSearch(){
        val uri = Uri.parse("movieapp://search")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
    private fun setImageEnd(drawable: Int) {
        binding?.tvLanguage?.setCompoundDrawablesWithIntrinsicBounds(
            null, null, ContextCompat.getDrawable(requireActivity(), drawable), null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}