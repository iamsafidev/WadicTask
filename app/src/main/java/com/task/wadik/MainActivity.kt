package com.task.wadik


import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.task.wadik.base.BaseActivity
import com.task.wadik.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Create Toolbar and set support action bar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        configureNavController()
    }

    private fun configureNavController() {
        // Get NavController
        val navController = findNavController(R.id.fragmentContainerView)
        // Get App Configuration from nav graph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        // Handles arrow back button
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}