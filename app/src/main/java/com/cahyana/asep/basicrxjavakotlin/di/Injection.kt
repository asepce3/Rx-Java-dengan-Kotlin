package com.cahyana.asep.basicrxjavakotlin.di

import android.content.Context
import com.cahyana.asep.basicrxjavakotlin.persistence.UserDao
import com.cahyana.asep.basicrxjavakotlin.persistence.UsersDatabase
import com.cahyana.asep.basicrxjavakotlin.ui.ViewModelFactory

object Injection {

    private fun provideUserDataSource(context: Context): UserDao {
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}