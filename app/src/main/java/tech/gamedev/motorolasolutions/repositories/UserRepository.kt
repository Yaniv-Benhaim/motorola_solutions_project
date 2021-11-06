package tech.gamedev.motorolasolutions.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.gamedev.motorolasolutions.data.db.UserDao
import tech.gamedev.motorolasolutions.data.other.Constants.USERS_TO_FETCH
import tech.gamedev.motorolasolutions.data.remote.api.UserApi
import tech.gamedev.motorolasolutions.utils.Resource
import tech.gamedev.motorolasolutions.utils.Status
import tech.gamedev.motorolasolutions.utils.extensions.getUser
import tech.gamedev.motorolasolutions.utils.extensions.showErrorMsg

class UserRepository(
    private val userDao: UserDao,
    private val userApi: UserApi,
    private val context: Context
    ) {

    val users = userDao.getUsers()
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun fetchNewUsers() {

        _isLoading.value = true
        try {
            //  Requesting a list of users from the api.
            val response = userApi.getUser(USERS_TO_FETCH)
            //  Setting resource to a custom object of resource for error handling.
            val resource = if(response.isSuccessful) {
                Resource(Status.SUCCESS, response.body(), null)
            } else {
            // Handling of a non successful request
                _isLoading.value = false
                Resource(Status.ERROR, null, response.errorBody().toString())
            }

            when(resource.status) {
                Status.LOADING -> Unit
                Status.ERROR -> showErrorMsg(context)
                Status.SUCCESS -> {

                    //  Clearing local database only if a new list was successfully received.
                    userDao.deleteAllUsers()
                    /*
                        Inserting new users to local database with the help of -
                        an extension function from utils/extensions/UserExt.kt.
                    */
                    resource.data?.results?.forEach {
                        userDao.insertUser(it.getUser())
                        _isLoading.value = false
                    }

                }
            }
        } catch (e: Exception) {
            /*
                Custom extension function from utils/extensions/ToastExt.kt -
                to show an error message in the form of a toast if something went wrong.
            */
            showErrorMsg(context)
            _isLoading.value = false
        }
    }
}