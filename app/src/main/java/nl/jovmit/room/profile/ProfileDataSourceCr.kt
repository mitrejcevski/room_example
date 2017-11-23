package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import nl.jovmit.room.app.AppDatabase
import java.io.IOException

internal class ProfileDataSourceCr(private val profileApi: ProfileApi,
                                   private val database: AppDatabase) : ProfileDataSource {

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        launch {
            fetchUserFromRepo(profileId)
        }
        val source = database.profileDao().findById(profileId)
        return Transformations.map(source) {
            ProfileResponse.Success(it ?: Profile())
        }
    }

    private suspend fun fetchUserFromRepo(userId: String) {
        async {
            try {
                val result = profileApi.getUser(userId).execute()
                result.body()?.let {
                    database.profileDao().insert(it)
                }
            } catch (exception: IOException) {
                Log.e("ProfileDataSource", "Error fetching profile", exception)
            }
        }
    }
}