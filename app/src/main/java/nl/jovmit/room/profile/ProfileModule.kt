package nl.jovmit.room.profile

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class ProfileModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi =
            retrofit.create(ProfileApi::class.java)

    @Provides
    fun provideDataSource(api: ProfileApi): ProfileDataSource =
            ProfileDataSourceCr(api)
}