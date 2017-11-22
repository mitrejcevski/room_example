package nl.jovmit.room.profile

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ProfileApi {

    @GET("/profile/{profileId}")
    fun getObservableUser(@Path("profileId") profileId: String): Observable<Profile>

    @GET("/profile/{profileId}")
    fun getUser(@Path("profileId") profileId: String): Call<Profile>
}