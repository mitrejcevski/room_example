package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

internal class ProfileViewModel @Inject constructor(private val dataSource: ProfileDataSource) : ViewModel() {

    private val inputLiveData = MutableLiveData<String>()
    private lateinit var profileResult: LiveData<ProfileResponse>

    fun fetchProfile(profileId: String) {
        inputLiveData.value = profileId
        profileResult = Transformations.switchMap(inputLiveData, dataSource::fetchProfile)
    }

    fun profile(): LiveData<ProfileResponse> = profileResult
}