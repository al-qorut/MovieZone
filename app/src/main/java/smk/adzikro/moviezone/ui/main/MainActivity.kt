package smk.adzikro.moviezone.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.databinding.ActivityMainBinding
import smk.adzikro.moviezone.ui.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var content: View
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(binding.root)
        splashScreen()
        setupView()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val nav = navHostFragment.navController
        binding.apply {
            bottomNav.setupWithNavController(nav)
            btnSearchMovie.setOnClickListener {
                showSearch("")
            }
        }
    }
    private fun splashScreen(){
        content = findViewById(android.R.id.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            content.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean =
                    when {
                        viewModel.mockDataLoading() -> {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        }
                        else -> false
                    }
            })
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    0f,
                    splashScreenView.width.toFloat()
                ).apply {
                    duration = 1000
                    doOnEnd {
                        splashScreenView.remove()
                    }
                }.also {
                    it.start()
                }
            }
        }
    }
    private fun setupView() {
            binding.menuMain.getToolbar().inflateMenu(R.menu.home_menu)
            binding.menuMain.toggleHideOnScroll(false)
            binding.menuMain.setupMenu()

            binding.menuMain.onSearchClosedListener = {
                //getAllFragments().forEach {
                //    it?.searchQueryChanged("")
               // }
            }

            binding.menuMain.onSearchTextChangedListener = { text ->
               if(text.length>=3){
                   showSearch(text)
               }
            }

            binding.menuMain.getToolbar().setOnMenuItemClickListener { menuItem ->


                when (menuItem.itemId) {
                    R.id.action_setting -> showSetting()
                    R.id.action_logout -> finish()
                    else -> return@setOnMenuItemClickListener false
                }
                return@setOnMenuItemClickListener true
            }
    }
    fun showSetting(){
        val navController = findNavController(R.id.main_nav_host)
        navController.navigateUp()
        navController.navigate(R.id.settingsFragment)
    }




    private fun showSearch(query:String){
        val uri = Uri.parse("movieapp://search")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.putExtra("search",query)
        startActivity(intent)
    }



}