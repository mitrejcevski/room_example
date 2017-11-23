package nl.jovmit.room.profile

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
internal interface ProfileDao {

    @Query("SELECT * FROM profile WHERE profileId LIKE :profileId")
    fun findById(profileId: String): LiveData<Profile>

    @Insert
    fun insert(profile: Profile)

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}