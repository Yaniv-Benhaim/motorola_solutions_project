package tech.gamedev.motorolasolutions.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.gamedev.motorolasolutions.databinding.ActivityMainBinding
import tech.gamedev.motorolasolutions.utils.ConnectionCheck

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectionCheck: ConnectionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
            Creating an instance of custom ConnectionCheck object -
            that shows if the device has an active internet connection -
            with the help of LiveData<Boolean>
        */
        connectionCheck = ConnectionCheck(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        connectionCheck.observe(this) {
            binding.tvInternetConnection.isVisible = !it
        }
    }
}