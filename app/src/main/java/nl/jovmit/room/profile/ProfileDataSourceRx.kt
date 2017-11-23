package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import nl.jovmit.room.app.AppDatabase

internal class ProfileDataSourceRx(private val profileApi: ProfileApi,
                                   private val database: AppDatabase) : ProfileDataSource {

    private val mediator = MediatorLiveData<ProfileResponse>()
    private val errorLiveData = MutableLiveData<String>()

    override fun fetchProfile(profileId: String): LiveData<ProfileResponse> {
        fetchProfileFromNetwork(profileId)
        val databaseSource = database.profileDao().findById(profileId)
        mediator.addSource(databaseSource) {
            mediator.postValue(ProfileResponse.Success(it ?: Profile()))
        }
        mediator.addSource(errorLiveData) {
            mediator.postValue(ProfileResponse.Error(it ?: ""))
        }
        return mediator
    }

    private fun fetchProfileFromNetwork(profileId: String) {
        profileApi.getObservableUser(profileId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(this::next, this::error)//these are called from background thread
    }

    private fun next(profile: Profile) {
        database.profileDao().insert(profile)
    }

    private fun error(error: Throwable) {
        errorLiveData.postValue(error.message ?: "network_error")
    }
}