package nl.jovmit.room.profile

internal sealed class ProfileResponse {

    internal data class Success(val profile: Profile) : ProfileResponse()

    internal data class Error(val error: String) : ProfileResponse()
}