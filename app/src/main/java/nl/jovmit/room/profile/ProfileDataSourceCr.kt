package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.IOException

internal class ProfileDataSourceCr(private val profileApi: ProfileApi) : ProfileDataSource {

    private val liveData = MutableLiveData<ProfileResponse>()

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        launch {
            val response = fetchUserFromRepo(profileId).await()
            liveData.postValue(response)
        }
        return liveData
    }

    private suspend fun fetchUserFromRepo(userId: String): Deferred<ProfileResponse> {
        return async {
            try {
                val result = profileApi.getUser(userId).execute()
                if (result.isSuccessful) {
                    ProfileResponse.Success(result.body() ?: Profile())
                } else {
                    ProfileResponse.Error(result.message())
                }
            } catch (exception: IOException) {
                ProfileResponse.Error(exception.message ?: "network_error")
            }
        }
    }
}