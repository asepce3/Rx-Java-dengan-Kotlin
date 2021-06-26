package com.cahyana.asep.basicrxjavakotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.cahyana.asep.basicrxjavakotlin.databinding.ActivityMainBinding
import com.cahyana.asep.basicrxjavakotlin.di.Injection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UserViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        binding.updateUserButton.setOnClickListener { updateUserName() }
    }

    override fun onStart() {
        super.onStart()

        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        disposable.add(viewModel.userName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({binding.userName.text = it},
                { error -> Log.e(TAG, "Unable to get username", error)}))
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }

    private fun updateUserName() {
        val userName = binding.userNameInput.text.toString()
        // Disable the update button until the user name update has been done
        binding.updateUserButton.isEnabled = false

        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        disposable.add(viewModel.updateUserName(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({binding.updateUserButton.isEnabled = true},
                { error -> Log.e(TAG, "Unable to update username", error)}))
    }

    companion object {
        private val TAG = UserActivity::class.java.simpleName
    }
}