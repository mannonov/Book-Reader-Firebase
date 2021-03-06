package uz.behadllc.bookreaderfirebase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import uz.behadllc.bookreaderfirebase.utils.Functions.makeInvisible
import uz.behadllc.bookreaderfirebase.utils.Functions.makeVisible
import uz.behadllc.bookreaderfirebase.utils.Variables
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import uz.behadllc.bookreaderfirebase.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var badge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavWithFragment()
        setupObserver()
    }

    private fun setupObserver() {
        Variables.downloadCount.observe(this, Observer {
            if (it != 0) {
                badge.isVisible = true
                badge.number = it
            } else badge.isVisible = false
        })
    }

    private fun setupBottomNavWithFragment() {
        bottomNavigationView.setupWithNavController(fragmentHost.findNavController())
        val navController = findNavController(R.id.fragmentHost)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> bottomNavigationView.makeInvisible()
                R.id.readBookFragment -> bottomNavigationView.makeInvisible()
                else -> bottomNavigationView.makeVisible()
            }
        }

        bottomNavigationView.setOnNavigationItemReselectedListener {
            bottomNavigationView.willNotDraw()
        }

        badge = bottomNavigationView.getOrCreateBadge(R.id.downloadFragment)
    }
}