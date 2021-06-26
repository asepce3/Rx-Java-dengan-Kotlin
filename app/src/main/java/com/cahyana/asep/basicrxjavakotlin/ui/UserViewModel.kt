package com.cahyana.asep.basicrxjavakotlin.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cahyana.asep.basicrxjavakotlin.persistence.User
import com.cahyana.asep.basicrxjavakotlin.persistence.UserDao
import io.reactivex.Completable
import io.reactivex.Flowable

class UserViewModel(private val dataSource: UserDao) : ViewModel() {

    fun userName(): Flowable<String> {
        return dataSource.getUserById(USER_ID)
            .map { user -> user.userName }
    }

    fun updateUserName(userName: String): Completable {
        return Completable.fromAction {
            val user = User(USER_ID, userName)
            dataSource.insertUser(user)
        }
    }

    companion object {
        const val USER_ID = "1"
    }
}
