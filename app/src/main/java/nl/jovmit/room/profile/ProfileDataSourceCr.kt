package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.launch
import nl.jovmit.room.app.AppDatabase
import java.io.IOException

internal class ProfileDataSourceCr(private val profileApi: ProfileApi,
                                   private val database: AppDatabase) : ProfileDataSource {

    private val mediator = MediatorLiveData<ProfileResponse>()
    private val errorLiveData = MutableLiveData<String>()

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        launch {
            fetchUserFromRepo(profileId)
        }
        val databaseSource = database.profileDao().findById(profileId)
        mediator.addSource(databaseSource) {
            mediator.postValue(ProfileResponse.Success(it ?: Profile()))
        }
        mediator.addSource(errorLiveData) {
            mediator.postValue(ProfileResponse.Error(it ?: "error"))
        }
        return mediator
    }

    private suspend fun fetchUserFromRepo(userId: String) {
        try {
            val result = profileApi.getUser(userId).execute()
            if (result.isSuccessful) {
                result.body()?.let {
                    database.profileDao().insert(it)
                }
            } else {
                errorLiveData.postValue(result.message())
            }
        } catch (exception: IOException) {
            errorLiveData.postValue(exception.message ?: "network_error")
        }
    }
}