package xyz.godi.core.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolBar()
    }

    private fun setupToolBar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}