package com.cahyana.asep.basicrxjavakotlin.persistense

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cahyana.asep.basicrxjavakotlin.persistence.User
import com.cahyana.asep.basicrxjavakotlin.persistence.UsersDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    var instantTastExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UsersDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
        UsersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getUsersWhenNoUserInserted() {
        database.userDao().getUserById("123")
            .test()
            .assertNoValues()
    }

    @Test
    fun insertAndGetUser() {
        database.userDao().insertUser(USER)
        database.userDao().getUserById(USER.id)
            .test()
            .assertValue {
                it.id == USER.id && it.userName == USER.userName
            }
    }

    @Test
    fun updateAndGetUser() {
        database.userDao().insertUser(USER)

        val updatedUser = User(USER.id, "new username")
        database.userDao().insertUser(updatedUser)

        database.userDao().getUserById(USER.id)
            .test()
            .assertValue {
                it.id == USER.id && it.userName == "new username"
            }
    }

    @Test
    fun deleteAndGetUser() {
        database.userDao().insertUser(USER)
        database.userDao().deleteAllUsers()
        database.userDao().getUserById(USER.id)
            .test()
            .assertNoValues()
    }

    companion object {
        private val USER = User("id", "username")
    }
}