package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers

internal class ProfileDataSourceRx constructor(private val profileApi: ProfileApi) :
        ProfileDataSource {

    private val result = MutableLiveData<ProfileResponse>()

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        profileApi.getObservableUser(profileId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(this::next, this::error) //these are called from background thread
        return result
    }

    private fun next(profile: Profile) {
        result.postValue(ProfileResponse.Success(profile))
    }

    private fun error(error: Throwable) {
        result.postValue(ProfileResponse.Error(error.message ?: "network_error"))
    }
}