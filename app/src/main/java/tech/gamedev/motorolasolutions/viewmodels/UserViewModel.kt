package tech.gamedev.motorolasolutions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.gamedev.motorolasolutions.data.other.Constants.SETUP_DELAY
import tech.gamedev.motorolasolutions.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

    val users = userRepo.users
    val isLoading = userRepo.isLoading

    init {
        checkLocalDatabase()
    }

    fun fetchNewUsers() = viewModelScope.launch { userRepo.fetchNewUsers() }

    private fun checkLocalDatabase() = viewModelScope.launch {
        delay(SETUP_DELAY)
        if(users.value.isNullOrEmpty()) {
            fetchNewUsers()
        }
    }
}