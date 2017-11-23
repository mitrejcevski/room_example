package nl.jovmit.room.profile

import dagger.Module
import dagger.Provides
import nl.jovmit.room.app.AppDatabase
import retrofit2.Retrofit

@Module
internal class ProfileModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi =
            retrofit.create(ProfileApi::class.java)

    @Provides
    fun provideDataSource(api: ProfileApi, database: AppDatabase): ProfileDataSource =
            ProfileDataSourceCr(api, database)
}