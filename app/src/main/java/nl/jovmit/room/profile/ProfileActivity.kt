package nl.jovmit.room.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nl.jovmit.room.R
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class ProfileActivity : DaggerAppCompatActivity() {

    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy(NONE) {
        ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.fetchProfile("id")
        viewModel.profile().observe(this, Observer {
            it?.let { updateUI(it) }
        })
    }

    private fun updateUI(response: ProfileResponse) {
        when (response) {
            is ProfileResponse.Success -> label.text = response.toString()
            is ProfileResponse.Error -> label.text = response.error
        }
    }
}
