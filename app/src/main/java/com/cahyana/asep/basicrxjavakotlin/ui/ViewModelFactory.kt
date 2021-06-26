package com.cahyana.asep.basicrxjavakotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cahyana.asep.basicrxjavakotlin.persistence.UserDao

class ViewModelFactory(private val dataSource: UserDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(dataSource) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
