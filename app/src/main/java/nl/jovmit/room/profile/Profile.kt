package nl.jovmit.room.profile

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Profile(@PrimaryKey var profileId: String = "",
                   var username: String = "",
                   var fullName: String = "",
                   var age: Int = 0)