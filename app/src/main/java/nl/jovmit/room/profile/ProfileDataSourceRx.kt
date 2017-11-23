package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.Log
import io.reactivex.schedulers.Schedulers
import nl.jovmit.room.app.AppDatabase

internal class ProfileDataSourceRx(private val profileApi: ProfileApi,
                                   private val database: AppDatabase) : ProfileDataSource {

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        profileApi.getObservableUser(profileId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(this::next, this::error) //these are called from background thread
        val source = database.profileDao().findById(profileId)
        return Transformations.map(source) {
            ProfileResponse.Success(it ?: Profile())
        }
    }

    private fun next(profile: Profile) {
        database.profileDao().insert(profile)
    }

    private fun error(error: Throwable) {
        Log.e("ProfileDataSource", "Error fetching profile", error)
    }
}