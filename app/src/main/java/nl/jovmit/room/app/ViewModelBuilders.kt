package nl.jovmit.room.app

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilders {

    @Binds
    internal abstract fun bindFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}