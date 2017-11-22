package nl.jovmit.room.profile

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import nl.jovmit.room.app.ViewModelKey

@Module(includes = arrayOf(ProfileModule::class))
internal abstract class ProfileBuilder {

    @ContributesAndroidInjector
    internal abstract fun profileActivity(): ProfileActivity

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: ProfileViewModel): ViewModel
}