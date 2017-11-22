package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData

internal interface ProfileDataSource {

    fun fetchProfile(profileId: String): LiveData<ProfileResponse>
}