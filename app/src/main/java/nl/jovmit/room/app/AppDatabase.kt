package nl.jovmit.room.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import nl.jovmit.room.profile.Profile
import nl.jovmit.room.profile.ProfileDao

@Database(entities = arrayOf(Profile::class), version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
}