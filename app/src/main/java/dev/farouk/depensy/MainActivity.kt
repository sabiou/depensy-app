package dev.farouk.depensy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.navHostFragment)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.onBoardingFragment -> bottomNav.visibility = View.INVISIBLE
                    else -> bottomNav.visibility = View.VISIBLE
                }
            }
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.navHostFragment).navigateUp()
}